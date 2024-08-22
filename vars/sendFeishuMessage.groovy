def call(String webhookUrl, String buildStatus, String jobName, String buildNumber, String buildUser, String duration, String startTime, String buildUrl) {
    def statusColor = buildStatus == 'SUCCESS' ? 'green' : 'red'
    def statusText = buildStatus == 'SUCCESS' ? '构建成功' : '构建失败'
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
                        "content": "**项目名称:** ${jobName}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**构建编号:** #${buildNumber}"
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
                        "content": "**触发者:** ${buildUser}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**构建时长:** ${duration.replace(' and counting', '')}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "**开始时间:** ${startTime}"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "[查看详情](${buildUrl})"
                    }
                },
                {
                    "tag": "div",
                    "text": {
                        "tag": "lark_md",
                        "content": "[构建日志](${buildUrl}console)"
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
        curl -X POST ${webhookUrl} \
        -H 'Content-Type: application/json' \
        -d '${message}'
    """
}
