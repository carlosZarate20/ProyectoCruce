package com.bbva.cruce.CruceDatos;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.bbva.cruce.CruceDatos.util.Constantes;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "com.bbva.cruce.CruceDatos") //Busqueda de componentes
public class CruceDatosApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CruceDatosApplication.class, args);
	}
	
	@Bean
	public DataSource dataSource() throws IOException 
	{	
		Properties properties = new Properties();
		
		String propertyPath = Constantes.PROPERTIES;
		Boolean existeArchivo = Files.exists(Paths.get(propertyPath));
		if (!existeArchivo) {
			throw new FileNotFoundException("Archivo no existe: " + propertyPath);
		}
		try (InputStream is = new FileInputStream(propertyPath)) {
			properties = new Properties();
			properties.load(is);
		}
		
		
		String proper = properties.getProperty("database.pool");
		
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		DataSource dataSource = dataSourceLookup.getDataSource(proper);
		return dataSource;
	}
}
