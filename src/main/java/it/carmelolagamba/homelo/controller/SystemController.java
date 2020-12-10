package it.carmelolagamba.homelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.carmelolagamba.homelo.config.ApplicationProperties;
import it.carmelolagamba.homelo.dto.system.InfoDto;

@RestController
@Api(value = "System Information")
public class SystemController {

	@Autowired
	private ApplicationProperties config;

	
	@ApiOperation(value = "Alive test")
	@RequestMapping(method = RequestMethod.GET, path = "/ping")
	public String ping() {
		return "pong";
	}

	@ApiOperation(value = "Get system info")
	@RequestMapping(method = RequestMethod.GET, path = "/info")
	public InfoDto info() {

		InfoDto infoDto = new InfoDto();

		infoDto.setEnvironment(config.getEnvironment());
		infoDto.setName(config.getName());
		infoDto.setUrl(config.getUrl());
		infoDto.setPort(config.getPort());

		return infoDto;
	}

}
