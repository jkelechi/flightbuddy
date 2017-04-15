package com.flightbuddy.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;

import com.flightbuddy.SearchInputData;
import com.flightbuddy.google.GoogleService;

@Service
public class TriggerTasksService {

	@Autowired GoogleService googleService;
	
	public Map<Runnable, Trigger> createTriggerTasks() {
		Map<Runnable, Trigger> triggerTasks = new HashMap<>();
    	addTriggerTasksForGoogle(triggerTasks);
		return triggerTasks;
	}

	private void addTriggerTasksForGoogle(Map<Runnable, Trigger> triggerTasks) {
		List<SearchInputData> searchInputDataList = googleService.getSearchInputData();
        Trigger trigger = googleService.getTrigger();
		for (SearchInputData searchInputData : searchInputDataList) {
			Runnable runnable = googleService.getTask(searchInputData);
	        triggerTasks.put(runnable, trigger);
		}
	}
}
