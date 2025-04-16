/*


package kr.co.koo;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration		//환경설정 => config.xml
@PropertySource("classpath:/application.properties")	//properties 파일을 로드할 수 있도록 하는 어노테이션 
public class dbinfos {
	
	//프로퍼티에써있는 저거로 시작하는 애들을 다 가져옴
	//노란줄은 마우스올려서 pom어쩌고 클릭하면 pom.xml 에 자동으로 추가됨 
//	@ConfigurationProperties(prefix = "spring.second-datasource")
	//요 위에줄 노란줄 해결 후 프로퍼티스 파일에도 생긴 노란줄도 클릭하면 META-INF에 자동으로 생김 
	//근데 그거는 첫번째꺼부터 first-이런식으로 썼을때 사용 지금은 그런식으로 안했음 
	//이미 오라클이 들어가있는상태 
	
	//히카리를 사용해서 연결! 
	@ConfigurationProperties(prefix = "spring.datasource.hikari")

	@Bean
	public DataSource mysqldb() {	//메소드명은 마음대로 만들기 
		return DataSourceBuilder.create().build();
	}

}



*/