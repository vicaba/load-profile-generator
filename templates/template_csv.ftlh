<#list outputs[0] as header>${header.name}(id:${header.id})<#sep>,</#list>
<#list outputs as output>
<#list output as data>${data.value}<#sep>,</#list>
</#list>