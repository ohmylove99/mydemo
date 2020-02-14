package org.octopus.api.util;

import java.util.UUID;

public class Utils {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
}
