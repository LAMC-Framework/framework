package com.gyvex.framework;

import java.util.Arrays;
import java.util.List;

import com.gyvex.framework.log.LogManager;

public class LAMCFramework extends LAMCPlugin {
	@Override
	protected void onLAMCEnable() {
		LogManager.info("Loaded plugin " + this.getName());
	}

	@Override
	protected void onLAMCDisable() {
		
	}

	@Override
	public List<String> configs() {
		return Arrays.asList(
			"database"
		);
	}
}
