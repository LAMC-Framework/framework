package com.gyvex.framework.models;

import com.gyvex.framework.database.Model;

public class PlayerModel extends Model<PlayerModel> {
	@Override
	protected String table() {
		return "players";
	}
}
