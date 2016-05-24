<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Testar no firefox !-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" doctype-system="about:legacy-compat" indent="yes"/>
	<xsl:template match="/">
		<xsl:apply-templates select='restaurante'>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="restaurante">
		<html>
			<head>
				<title>Restaurante:<xsl:value-of select="@designação"/>
				</title>
			</head>
			<h1>
				<xsl:value-of select="@designação"/>
			</h1>
			<img alt="Logotipo" src="{concat('data:image/jpg;base64,',imagem)}"/>
		</html>
	</xsl:template>
</xsl:stylesheet>
