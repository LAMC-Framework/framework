package com.gyvex.framework.models;

import com.gyvex.framework.database.Model;

public class MigrationModel extends Model {

	@Override
	protected String table() {
		return "migrations";
	}

}
