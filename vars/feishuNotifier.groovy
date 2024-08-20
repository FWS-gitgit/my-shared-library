def sendNotification(String status) {
    def feishuWebhook = 'https://open.feishu.cn/open-apis/bot/v2/hook/e6d11172-ab2b-4b94-9271-7e05d4412056'
    def statusColor = status == 'SUCCESS' ? 'green' : 'red'
    def statusText = status == 'SUCCESS' ? '构建成功' : '构建失败'
    def message = """{
        "msg_type": "interactive",
        "card": {
            "header": {
                "title": {
                    "tag": "plain_text",
                    "content": "Jenkins Pipeline 构建通知"
                },
                "template": "${statusColor}"
            },
            "elements": [
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**项目名称:** ${env.JOB_NAME}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**构建编号:** #${env.BUILD_NUMBER}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**构建状态:** ${statusText}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**触发者:** ${env.BUILD_USER}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**构建时长:** ${currentBuild.durationString.replace(' and counting', '')}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**开始时间:** ${new Date(currentBuild.startTimeInMillis).format('yyyy-MM-dd HH:mm:ss')}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "[查看详情](${env.BUILD_URL})"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "[构建日志](${env.BUILD_URL}console)"
                    }
                },
                {
                    "tag": "hr"
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "_备注: 构建完成后请及时检查结果_"
                    }
                }
            ]
        }
    }"""

    sh """
        curl -X POST ${feishuWebhook} \
        -H 'Content-Type: application/json' \
        -d '${message}'
    """
}

