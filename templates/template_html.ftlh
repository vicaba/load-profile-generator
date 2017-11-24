<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Welcome!</title>
    </head>
    <body>
        <h1>Data Outputs</h1>
        <table style="width:100%">
            <tr>
                <#list outputs[0] as header>
                <th>${header.type}</th>
                </#list>
            </tr>
            <#list outputs as datalist>
            <tr>
                <#list datalist as data>
                <td>${data.value}</td>
                </#list>
            </tr>
            </#list>
        </table>
    </body>
</html>