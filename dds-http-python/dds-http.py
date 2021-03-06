#!/usr/bin/env python
import time
import hmac
import requests
from uuid import uuid4
from hashlib import sha1

alias = "prod"
# 使用自己产品的相关参数替换下列参数。
productId = "x"
apikey = "x"


def textDm(url):
    # 发送文本请求对话。
    content = {
        "aiType": "dm",  # aiType 设置为 "dm"
        "topic": 'nlu.input.text',
        "recordId": uuid4().hex,
        "refText": "苏州的天气"
    }
    r = requests.post(url, json=content)
    print(r.text)

def triggerIntent(url):
    # 发送触发意图请求。
    content = {
        "aiType":"dm",
        "topic": "dm.input.intent",
        "recordId": uuid4().hex,
        "skillId": "2018040200000004",
        "task": "查询天气",
        "intent": "天气",
        "slots": {
            "国内城市": "苏州",
        }
    }
    r = requests.post(url, json=content)
    print(r.text)


def systemSetting(url):
    # 做系统级配置。
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
    r = requests.post(url, json=content)
    print(r.text)


def skillSetting(url):
    # 做技能级配置。
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
    
    r = requests.post(url, json=content)
    print(r.text)


if __name__ == "__main__":
    # 云端对云端。
    url = f"https://dds.dui.ai/dds/v2/{alias}?productId={productId}&apikey={apikey}"

    textDm(url)
    # triggerIntent(url)
    # systemSetting(url)
    # skillSetting(url)
