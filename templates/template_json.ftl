[
    <#list outputs as output>
    [
        <#list output as data>
        {
            "id": "${data.id?json_string}",
            "${data.type?json_string}": "${data.value?json_string}"
        }
        <#sep>,
        </#list>
    ]
    <#sep>,
    </#list>
]