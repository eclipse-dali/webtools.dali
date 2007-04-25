/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import java.util.StringTokenizer;

public class RelationshipMappingTools
{	
	public static boolean targetEntityIsValid(String targetEntity) {
		if (targetEntity == null) {
			return true;
		}
		// balance is # of name tokens - # of period tokens seen so far
		// initially 0; finally 1; should never drop < 0 or > 1
		int balance = 0;
		for (StringTokenizer t = new StringTokenizer(targetEntity, ".", true); t.hasMoreTokens();) {
			String s = t.nextToken();
			if (s.indexOf('.') >= 0) {
				// this is a delimiter
				if (s.length() > 1) {
					// too many periods in a row
					return false;
				}
				balance--;
				if (balance < 0) {
					return false;
				}
			} else {
				// this is an identifier segment
				balance++;
			}
		}
		return (balance == 1);
	}
}
