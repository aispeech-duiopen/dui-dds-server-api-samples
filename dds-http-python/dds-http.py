#!/usr/bin/env python
import time
import hmac
import requests
from uuid import uuid4
from hashlib import sha1

alias = "prod"
productId = "278578029"
deviceName = "33f35b18c34f8c35c2521e408b3733b8"
deviceSecret = '14511da111e246909a59f4fc6020cfdd'


def signature(sig_factors):
    # 计算签名。
    keys = list(sig_factors.keys())
    keys.sort()
    tmp = ""
    for key in keys:
        tmp += sig_factors[key]

    hashed = hmac.new(bytes(deviceSecret, encoding="utf-8"),
                      bytes(tmp, encoding="utf-8"), sha1)
    return hashed.hexdigest()


def textDm():
    # 发送文本请求对话。
    nonce = uuid4().hex
    timestamp = int(time.time())
    nonce = uuid4().hex
    factors = {
        "deviceName": deviceName,
        "nonce": nonce,
        "productId": productId,
        "timestamp": str(timestamp)
    }
    sig = signature(factors)

    content = {
        "aiType": "dm",  # aiType 设置为 "dm"
        "topic": 'nlu.input.text',
        "recordId": uuid4().hex,
        "refText": "苏州的天气"
    }
    url = f"https://dds.dui.ai/dds/v2/{alias}?productId={productId}&deviceName={deviceName}&nonce={nonce}&timestamp={timestamp}&sig={sig}"
    r = requests.post(url, json=content)
    print(r.text)


def textNlu():
    # 发送文本请求语义。
    nonce = uuid4().hex
    timestamp = int(time.time())
    nonce = uuid4().hex
    factors = {
        "deviceName": deviceName,
        "nonce": nonce,
        "productId": productId,
        "timestamp": str(timestamp)
    }
    sig = signature(factors)

    content = {
        "aiType": "nlu",  # aiType 设置为 "nlu"
        "topic": 'nlu.input.text',
        "recordId": uuid4().hex,
        "refText": "苏州的天气"
    }
    url = f"https://dds.dui.ai/dds/v2/{alias}?productId={productId}&deviceName={deviceName}&nonce={nonce}&timestamp={timestamp}&sig={sig}"
    r = requests.post(url, json=content)
    print(r.text)


def systemSetting():
    # 做系统级配置。
    nonce = uuid4().hex
    timestamp = int(time.time())
    nonce = uuid4().hex
    factors = {
        "deviceName": deviceName,
        "nonce": nonce,
        "productId": productId,
        "timestamp": str(timestamp)
    }
    sig = signature(factors)

    content = {
        "topic": "system.settings",
        "settings": [
            {
                "key": "location",
                "value": {
                    "longitude": "80",
                    "latitude": "120",
                    "address": "china",
                    "city": "suzhou",
                    "time": "2019-04-01T09:00:00+0800"
                }
            }
        ]
    }
    url = f"https://dds.dui.ai/dds/v2/{alias}?productId={productId}&deviceName={deviceName}&nonce={nonce}&timestamp={timestamp}&sig={sig}"
    r = requests.post(url, json=content)
    print(r.text)


def skillSetting():
    # 做技能级配置。
    nonce = uuid4().hex
    timestamp = int(time.time())
    nonce = uuid4().hex
    factors = {
        "deviceName": deviceName,
        "nonce": nonce,
        "productId": productId,
        "timestamp": str(timestamp)
    }
    sig = signature(factors)

    content = {
        "topic": "skill.settings",
        "skillId": "2018040200000004",
        "settings": [
            {
                "key": "key1",
                "value": "value1"
            }
        ]
    }
    url = f"https://dds.dui.ai/dds/v2/{alias}?productId={productId}&deviceName={deviceName}&nonce={nonce}&timestamp={timestamp}&sig={sig}"
    r = requests.post(url, json=content)
    print(r.text)


if __name__ == "__main__":
    textDm()
    # textNlu()
    # systemSetting()
    # skillSetting()
