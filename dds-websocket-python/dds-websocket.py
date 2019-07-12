#!/usr/bin/env python
import json
import time
import hmac
import asyncio
#import ssl
import websockets
from hashlib import sha1
from uuid import uuid4

alias = "test"
audioFile = "8k.wav"

# 使用自己产品的相关参数替换下列参数。
productId = "x"
# 设备对云端。
deviceName = "x"
deviceSecret = "x"
# 云端对云端。
apikey = "x"


def signature(sig_factors):
    keys = list(sig_factors.keys())
    keys.sort()
    tmp = ""
    for key in keys:
        tmp += sig_factors[key]

    print(tmp)
    hashed = hmac.new(bytes(deviceSecret, encoding="utf-8"),
                      bytes(tmp, encoding="utf-8"), sha1)
    return hashed.hexdigest()


async def textRequest(ws):
    content = {
        "aiType":"dm",
        "topic": 'nlu.input.text',
        "recordId": uuid4().hex,
        "refText": "姚明是谁"
    }
    try:
        await ws.send(json.dumps(content))
        resp = await ws.recv()
        print(resp)
    except websockets.exceptions.ConnectionClosed as exp:
        print(exp)


async def triggerIntent(ws):
    content = {
        "aiType":"dm",
        'topic': 'dm.input.intent',
        'recordId': uuid4().hex,
        'skillId': '2018040200000004',
        'intent': '查询天气',
        'task': "天气",
        'slots': {
            '城市': "苏州"
        }
    }
    try:
        await ws.send(json.dumps(content))
        resp = await ws.recv()
        print(resp)
    except websockets.exceptions.ConnectionClosed as exp:
        print(exp)


async def audioRequest(ws):
    content = {
        "aiType":"dm",
        "topic": "recorder.stream.start",
        "recordId": uuid4().hex,
        "audio": {
            "audioType": "wav",
            "sampleRate": 8000,
            "channel": 1,
            "sampleBytes": 2
        }
    }
    try:
        await ws.send(json.dumps(content))
        with open(audioFile, 'rb') as f:
            while True:
                chunk = f.read(3200)
                if not chunk:
                    await ws.send(bytes("", encoding="utf-8"))
                    break
                await ws.send(chunk)
        async for message in ws:
            print(message)
            resp = json.loads(message)
            if 'dm' in resp:
                break
    except websockets.exceptions.ConnectionClosed as exp:
        print(exp)
        ws.close()


async def dds_demo():
    # 云端对云端。
    # url = f"wss://dds.dui.ai/dds/v2/{alias}?serviceType=websocket&productId={productId}&apikey={apikey}"

    # 设备对云端。
    nonce = uuid4().hex
    timestamp = int(time.time())
    nonce = uuid4().hex
    factors = {
        "productId": productId,
        "deviceName": deviceName,
        "timestamp": str(timestamp),
        "nonce": nonce
    }
    sig = signature(factors)
    url = f"wss://dds.dui.ai/dds/v2/{alias}?serviceType=websocket&productId={productId}&deviceName={deviceName}&nonce={nonce}&timestamp={timestamp}&sig={sig}"
    
    print(url)
    async with websockets.connect(url) as websocket:
        await textRequest(websocket)
        # await triggerIntent(websocket)
        # await audioRequest(websocket)


asyncio.get_event_loop().run_until_complete(dds_demo())
