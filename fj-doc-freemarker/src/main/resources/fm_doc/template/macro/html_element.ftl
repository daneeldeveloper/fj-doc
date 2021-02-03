<#global defaultBorderColor='black'>
<#global defaultFontSize='10'>

<#macro handleElement current>
	<#assign elementType="${current.class.simpleName}"/>
	<#if elementType = 'DocPhrase'>
		<#if (current.link)??>
		<a <@handleStyleOnly styleValue=current.style/> href="${current.link}">${current.text}</a>
		<#elseif (current.anchor)??>
		<a <@handleStyleOnly styleValue=current.style/> name="${current.anchor}">${current.text}</a>
		<#else>
		<span <@handleStyleOnly styleValue=current.style/>>${current.text}</span>
		</#if>
	<#elseif elementType = 'DocNbsp'>
		&nbsp;
	<#elseif elementType = 'DocBr'>
		<br/>		
	<#elseif elementType = 'DocPara'>
		<#if current.headLevel == 0>
			<p <@handleStyleComplete styleValue=current.style alignValue=current.align/>>${current.text}</p>
		<#else>
			<h${current.headLevel} <@handleStyleComplete styleValue=current.style alignValue=current.align/>>${current.text}</h${current.headLevel}>
		</#if>
	<#elseif elementType = 'DocTable'>
		<@handleTable docTable=current/>			
	<#else>
		<span>Element type non implemented yet : ${elementType}</span>
	</#if>
</#macro>

<#macro handleTable docTable>
	<table style='width: ${docTable.width}%'>
		<#list docTable.elementList as row>	
		<tr>
			<#list row.elementList as cell>
				<td id="cell_${row?index}_${cell?index}" style="width: ${docTable.colWithds[cell?index]}%; <@handleAlign alignValue=cell.align/> <@handleBorders docBorders=cell.docBorders/>"  <@handleColspan colspanValue=cell.columnSpan/> <@handleRowspan rowspanValue=cell.rowSpan/>> 
					<#list cell.elementList as cellElement>
					<@handleElement current=cellElement/>
					</#list>
				</td>
			</#list>
		</tr>				
		</#list>
	</table>
</#macro>

<#macro handleBorder mode size color><#if size != 0><#if size = -1><#assign calcSize="1"/><#else><#assign calcSize="${size}"/></#if>${mode}: ${calcSize}px solid ${color}; </#if></#macro>

<#macro handleBorders docBorders>
	<#if (docBorders.borderColorTop)??><#assign borderColorTop="${docBorders.borderColorTop}"/><#else><#assign borderColorTop="${defaultBorderColor}"/></#if>
	<#if (docBorders.borderColorBottom)??><#assign borderColorBottom="${docBorders.borderColorBottom}"/><#else><#assign borderColorBottom="${defaultBorderColor}"/></#if>
	<#if (docBorders.borderColorLeft)??><#assign borderColorLeft="${docBorders.borderColorLeft}"/><#else><#assign borderColorLeft="${defaultBorderColor}"/></#if>
	<#if (docBorders.borderColorRight)??><#assign borderColorRight="${docBorders.borderColorRight}"/><#else><#assign borderColorRight="${defaultBorderColor}"/></#if>
	<@handleBorder mode='border-top' size=docBorders.borderWidthTop color=borderColorTop/>
	<@handleBorder mode='border-bottom' size=docBorders.borderWidthBottom color=borderColorBottom/>
	<@handleBorder mode='border-left' size=docBorders.borderWidthLeft color=borderColorLeft/>
	<@handleBorder mode='border-right' size=docBorders.borderWidthRight color=borderColorRight/>
</#macro>

<#macro handleRowspan rowspanValue> rowspan="${rowspanValue}" </#macro>

<#macro handleColspan colspanValue> colspan="${colspanValue}" </#macro>

<#macro handleAlign alignValue><#if alignValue = 1>text-align: left;<#elseif alignValue = 2>text-align: center;<#elseif alignValue = 3>text-align: right;</#if></#macro>

<#macro handleStyle styleValue><#if styleValue = 2>font-weight: bold;<#elseif styleValue = 3>font-weight: underline;<#elseif styleValue = 4>font-style: italic;<#elseif styleValue = 5>font-weight: bold; font-style: italic;</#if></#macro>

<#macro handleStyleOnly styleValue><#assign cStyle><@handleStyle styleValue=styleValue/></#assign><#if cStyle != '' >style="${cStyle}"</#if></#macro>

<#macro handleStyleComplete styleValue alignValue><#assign cStyle><@handleStyle styleValue=styleValue/></#assign><#assign cAlign><@handleAlign alignValue=alignValue/></#assign><#if cStyle != '' || cAlign != '' >style="${cStyle} ${cAlign}"</#if></#macro>

