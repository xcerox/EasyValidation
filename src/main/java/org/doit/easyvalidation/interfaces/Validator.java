package org.doit.easyvalidation.interfaces;

import org.doit.easyvalidation.exceptions.InternalException;
import org.json.JSONException;
import org.json.JSONObject;

@FunctionalInterface
public interface Validator <T> {
	boolean isValid(T value, JSONObject propierties) throws InternalException, JSONException;
}
