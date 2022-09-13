<html xmlns="http://www.w3.org/1999/xhtml" lang="ru">
<head>
    <meta charset=utf-8>
    <title>Пользователи</title>
    <script>
        function getClientById() {
            const clientIdTextBox = document.getElementById('clientIdTextBox');
            const clientDataContainer = document.getElementById('clientDataContainer');

            const id = clientIdTextBox.value;
            fetch('api/client/' + id)
                .then(response => response.json())
                .then(client => clientDataContainer.innerHTML = JSON.stringify(client));
        }

        async function addClient() {
            const name = document.getElementById('addClientName').value;
            const address = document.getElementById('addClientAddress').value;
            const phoneElements = document.getElementsByName('addClientPhoneNumber');
            const clientSavedDataContainer = document.getElementById('clientSavedDataContainer');
            let phones = [];
            for (let i = 0; i < phoneElements.length; i++) {
                console.log(i);
                if (phoneElements[i].value !== "") {
                    phones[i] = phoneElements[i].value
                }
            }
            await fetch('api/client', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: name,
                    address: {
                        street: address
                    },
                    phones: phones
                })
            }).then(response => response.json())
              .then(client => clientSavedDataContainer.innerHTML = JSON.stringify(client))
              .catch(error => clientSavedDataContainer.innerHTML = error.message);
        }

        function addPhoneNumberInput() {
            let x = document.createElement("INPUT");
            let br = document.createElement("BR");

            x.setAttribute("type", "text");
            x.setAttribute("name", "addClientPhoneNumber");
            x.setAttribute("placeholder", "Укажите дополнительный номер телефона");
            document.getElementById("addClient").appendChild(x).appendChild(br);
            document.getElementById("addClient").appendChild(br);
        }
    </script>
</head>

<body>
<h4>Добавить нового пользователя</h4>
<form method="post" id="addClient">
    <div>Имя:</div>
    <input type="text" id="addClientName" required pattern="[A-Za-z0-9]{1,50}"/><br/>
    <div>Адрес:</div>
    <input type="text" id="addClientAddress"/><br/>
    <div>Номер телефона:</div>
    <input type="text" id="addClientPhoneNumber" name="addClientPhoneNumber" pattern="[A-Za-z0-9- ]{1,50}"/>
    <button type="button" onclick="addPhoneNumberInput()">Добавить еще номер телефона</button>
    <br/>
</form>
<button type="button" onclick="addClient()">Добавить</button><br/>
<pre id="clientSavedDataContainer"></pre>
<br/><hr/>

<h4>Получить пользователя по id</h4>
<input type="text" id="clientIdTextBox" value="3" placeholder="Введите id клиента">
<button onclick="getClientById()">Получить</button>
<pre id="clientDataContainer"></pre>
<br/><hr/>

<h4>Случайный пользователь</h4>
<table style="width: 400px">
    <thead>
        <tr>
            <td style="width: 50px">Id</td>
            <td style="width: 150px">Имя</td>
            <td style="width: 100px">Адрес</td>
            <td style="width: 100px">Телефоны</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>${randomClient.id}</td>
            <td>${randomClient.name!""}</td>
            <td>
                <#if randomClient.address?has_content>
                    <#if randomClient.address.street?has_content && randomClient.address.street?trim != '' >
                        ${randomClient.address.street}
                    <#else>
                        Empty address
                    </#if>
                <#else>
                    No address
                </#if>
            </td>
            <td>
                <#if randomClient.phones?has_content>
                    <#list randomClient.phones as phone>
                        <#if phone.number?has_content && phone.number?trim != '' >
                            ${phone.number}<#sep>,
                        </#if>
                    </#list>
                <#else>
                   No phone numbers
                </#if>
            </td>
        </tr>
    </tbody>
</table>


<h4>Все пользователи:</h4>
<table style="width: 400px">
    <thead>
    <tr>
        <td style="width: 50px">Id</td>
        <td style="width: 150px">Имя</td>
        <td style="width: 100px">Адрес</td>
        <td style="width: 100px">Телефоны</td>
    </tr>
    </thead>
    <tbody>
    <#list clients as client>
        <tr>
            <td>${client.id}</td>
            <td>${client.name!""}</td>
            <td>
                <#if client.address?has_content>
                    <#if client.address.street?has_content && client.address.street?trim != '' >
                        ${client.address.street}
                    <#else>
                        Empty address
                    </#if>
                <#else>
                    No address
                </#if>
            </td>
            <td>
                <#if client.phones?has_content>
                    <#list client.phones as phone>
                        <#if phone.number?has_content && phone.number?trim != '' >
                            ${phone.number}<#sep>,
                        </#if>
                    </#list>
                <#else>
                    No phone numbers
                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>
