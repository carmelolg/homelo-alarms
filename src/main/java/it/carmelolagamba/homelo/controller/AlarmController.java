package it.carmelolagamba.homelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.carmelolagamba.homelo.model.Alarm;
import it.carmelolagamba.homelo.service.AlarmService;

@RestController
@Api(value = "Alarm controller")
public class AlarmController {

	@Autowired
	private AlarmService alarmService;

	@ApiOperation(value = "Alarm insert")
	@RequestMapping(method = RequestMethod.POST, path = "/alarm")
	public String manageAlarm(@RequestParam("house") String house, @RequestParam("enable") boolean enable) {

		if(enable) {
			alarmService.enable(house);
			return "ok";
		}else {		
			Alarm alarm = new Alarm(house, true);
			boolean check = alarmService.disable(alarm);
			return check ? "ok" : "ko";
		}
	}
	
	@ApiOperation(value = "Get alarm")
	@RequestMapping(method = RequestMethod.GET, path = "/alarm")
	public boolean getAlarm(@RequestParam("house") String house) {
		return alarmService.isActive(house);
	}

}
