package com.dbdeploy;

import com.dbdeploy.scripts.ChangeScript;

import java.util.ArrayList;
import java.util.List;

public class PrettyPrinter {

	public String format(List<Long> appliedChanges) {
		if (appliedChanges.isEmpty()) {
			return "(none)";
		}
		
		StringBuilder builder = new StringBuilder();
		
		Long lastRangeStart = null;
		Long lastNumber = null;
		
		for (Long thisNumber : appliedChanges) {
			if (lastNumber == null) {
				// first in loop
				lastNumber = thisNumber;
				lastRangeStart = thisNumber;
			} else if (thisNumber == lastNumber + 1) {
				// continuation of current range
				lastNumber = thisNumber;
			} else {
				// doesn't fit into last range - so output the old range and
				// start a new one
				appendRange(builder, lastRangeStart, lastNumber);
				lastNumber = thisNumber;
				lastRangeStart = thisNumber;
			}
		}

		appendRange(builder, lastRangeStart, lastNumber);
		return builder.toString();
	}

	private void appendRange(StringBuilder builder, Long lastRangeStart, Long lastNumber) {
		if (lastRangeStart == lastNumber) {
			appendWithPossibleComma(builder, lastNumber);
		} else if (lastRangeStart + 1 == lastNumber) {
			appendWithPossibleComma(builder, lastRangeStart);
			appendWithPossibleComma(builder, lastNumber);
		} else {
			appendWithPossibleComma(builder, lastRangeStart + ".." + lastNumber);
		}
	}

	private void appendWithPossibleComma(StringBuilder builder, Object o) {
		if (builder.length() != 0) {
			builder.append(", ");
		}
		builder.append(o);
	}

	public String formatChangeScriptList(List<ChangeScript> changeScripts) {
		List<Long> numberList = new ArrayList<Long>(changeScripts.size());
		
		for (ChangeScript changeScript : changeScripts) {
			numberList.add(changeScript.getId());
		}
		
		return format(numberList);
	}

}
