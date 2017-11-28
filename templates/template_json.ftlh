[
<#list outputs as output>
    [
<#list output as data>
        {
            "id": "${data.id?json_string}",
            "name": "${data.name?json_string}",
            "type": "${data.type?json_string}",
            "value": "${data.value?json_string}"
        }<#sep>,
</#list>

    ]<#sep>,
</#list>

]