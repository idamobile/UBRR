<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="text" />
	<xsl:template match="/">
		<xsl:for-each select="message/item">
			<xsl:value-of select="key" />
			<xsl:text>: </xsl:text>
			<xsl:for-each select="values/value">
				<xsl:value-of select="text()" />
				<xsl:text>&#x20;</xsl:text>
			</xsl:for-each>
			<xsl:text>&#xa;</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>