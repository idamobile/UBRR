general {
	webAppRootKey	= 'UBRR'
}

environments {
	dev {
		jdbcUrl		= 'jdbc:oracle:thin:@project.idamob.ru:1521:XE'
		logLevel	= 'DEBUG'
	}
	
	test {
		jdbcUrl		= 'jdbc:oracle:thin:@project.idamob.ru:1521:XE'
		logLevel	= 'WARN'
	}
	
	prod {
		jdbcUrl		= 'jdbc:oracle:thin:@127.0.0.1:ida'
		logLevel	= 'ERROR'
	}
}