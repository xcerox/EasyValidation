package org.doit.project.easyvalidation.interfaces;

import org.doit.project.easyvalidation.exceptions.InternalException;
import org.json.JSONException;
import org.json.JSONObject;

public interface Validator <T> {
	boolean doValidate(T tipo, JSONObject propierties) throws InternalException, JSONException;
}
