window.onload = function() {
    const form = document.getElementById('jsonForm');
    const textarea = document.getElementById('jsonData');
    const responseForm = document.getElementById('responseForm');
    const responseData = document.getElementById('responseData');

    // 动态调整文本区域高度的函数
    function adjustTextareaHeight(textarea) {
        textarea.style.height = 'auto';
        textarea.style.height = (textarea.scrollHeight) + 'px';
    }

    // 初次加载时调整文本区域高度
    adjustTextareaHeight(textarea);

    form.onsubmit = function(event) {
        event.preventDefault(); // 防止表单提交默认行为

        const input = textarea.value;

        if (input == null || input.trim() === '') {
            alert('输入不能为空，请重新输入！');
            return;
        }

        if (!validateJson(input)) {
            alert('输入的JSON格式有误，请重新输入！');
            return;
        }

        // 如果输入通过验证，则进行数据处理
        const warningRequests = JSON.parse(input);
        fetch('/api/warn', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(warningRequests)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                responseData.value = JSON.stringify(data, null, 2);
                responseForm.style.display = 'block';
                adjustTextareaHeight(responseData);
            })
            .catch(error => {
                console.error('Error:', error);
                responseData.value = 'An error occurred!\n' + error;
                responseForm.style.display = 'block';
                adjustTextareaHeight(responseData);
            });
    };

    function validateJson(json) {
        try {
            JSON.parse(json);
            return true;
        } catch (e) {
            return false; // 如果JSON解析失败，返回false
        }
    }

    // 调整输入框的高度以适应内容
    textarea.addEventListener('input', () => adjustTextareaHeight(textarea));
};
