package org.octopus.api.util;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author joshualeng
 *
 */
public class Utils {
	/**
	 * 
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	/**
	 * 
	 * @param pageableReq
	 * @param page
	 * @param size
	 * @param sortBy
	 * @param direction
	 * @return
	 */
	public static Pageable sort(Pageable pageableReq, Integer page, Integer size, String sortBy, String direction) {
		Pageable pageable = null;
		if (page != null && size != null) {
			if ("ASC".equalsIgnoreCase(direction))
				pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
			else if ("DESC".equalsIgnoreCase(direction))
				pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
		} else {
			pageable = pageableReq;
		}
		return pageable;
	}
}
