<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="text" />
	<xsl:template match="/">
	    <xsl:text>Добрый день!&#xa;&#xa;</xsl:text>
	    
		<xsl:for-each select="message/item">
			<xsl:choose>
				<xsl:when test="key = 'last_name'">
					<xsl:text>Фамилия</xsl:text>
				</xsl:when>
				<xsl:when test="key = 'first_name'">
					<xsl:text>Имя</xsl:text>
				</xsl:when>
				<xsl:when test="key = 'middle_name'">
					<xsl:text>Отчество</xsl:text>
				</xsl:when>
				<xsl:when test="key = 'phone'">
					<xsl:text>Телефон</xsl:text>
				</xsl:when>
				<xsl:when test="key = 'message'">
					<xsl:text>Текст сообщения</xsl:text>
				</xsl:when>
				<xsl:when test="key = 'credit_amount'">
					<xsl:text>Сумма</xsl:text>
				</xsl:when>
				<xsl:when test="key = 'credit_period'">
					<xsl:text>Срок</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="key" />
				</xsl:otherwise>
			</xsl:choose>
		
			<xsl:text>: </xsl:text>
			<xsl:for-each select="values/value">
				<xsl:value-of select="text()" />
				<xsl:text>&#x20;</xsl:text>
			</xsl:for-each>
			<xsl:text>&#xa;</xsl:text>
		</xsl:for-each>
		
		<xsl:text>&#xa;ОАО «УБРиР»&#xa;8(800)1000-200&#xa;www.twitter.com/Bank_UBRR&#xa;www.ubrr.ru</xsl:text>
	</xsl:template>
</xsl:stylesheet>