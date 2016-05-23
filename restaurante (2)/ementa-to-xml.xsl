<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Eng. P. Filipe !-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>
	
	<xsl:param name="modo" select="'diaútil'"/>
	<xsl:param name="tipo" select="'almoço'"/>
	<xsl:template match="/restaurante">
		<restaurante coordenadas='{@coordenadas}' designação='{@designação}' email='{@email}'>
			<logotipo mime="{imagem/@mime}" path="{imagem/@path}" encoding="{imagem/@encoding}">
				<xsl:value-of select="imagem"/>
			</logotipo>
			<xsl:apply-templates select="ementas/ementa[@modo=$modo and @tipo=$tipo]"/>
		</restaurante>
	</xsl:template>
	<xsl:template match="ementas/ementa[@modo=$modo and @tipo=$tipo]">
		<ementa modo='{@modo}' tipo='{@tipo}'>
			<entrada>
				<xsl:call-template name="servir">
					<xsl:with-param name="tipo" select="'entrada'"/>
				</xsl:call-template>
			</entrada>
			<carne>
				<xsl:call-template name="servir">
					<xsl:with-param name="tipo" select="'carne'"/>
				</xsl:call-template>
			</carne>
			<peixe>
				<xsl:call-template name="servir">
					<xsl:with-param name="tipo" select="'peixe'"/>
				</xsl:call-template>
			</peixe>
			<doce>
				<xsl:call-template name="servir">
					<xsl:with-param name="tipo" select="'doce'"/>
				</xsl:call-template>
			</doce>
			<fruta>
				<xsl:call-template name="servir">
					<xsl:with-param name="tipo" select="'fruta'"/>
				</xsl:call-template>
			</fruta>
		</ementa>
	</xsl:template>
	<xsl:template name='servir'>
		<xsl:param name="tipo"/>
		<xsl:for-each select="serve[@iditem=//itens/item[@tipo=$tipo]/@iditem]">
			<xsl:sort select="@iditem"/>
			<xsl:variable name="id" select="@iditem"/>
			<serve preço='{@preço}'>
				<xsl:value-of select="//itens/item[@iditem=$id]/pt-pt/text()"/> (<xsl:value-of select="//itens/item[@iditem=$id]/en-uk/text()"/> / <xsl:value-of select="//itens/item[@iditem=$id]/fr-fr/text()"/>)
				<xsl:for-each select="//ingredientes/ingrediente[@idingrediente=//item[@iditem=$id]/ingrediente/@idingrediente]">
					<ingrediente>
						<xsl:value-of select="pt-pt/text()"/> (<xsl:value-of select="en-uk/text()"/> / <xsl:value-of select="fr-fr/text()"/>)
					</ingrediente>
				</xsl:for-each>
			</serve>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
