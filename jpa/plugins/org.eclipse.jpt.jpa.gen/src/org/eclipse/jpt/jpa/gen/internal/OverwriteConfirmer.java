/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.gen.internal;

import org.eclipse.jpt.common.utility.internal.StringTools;

public interface OverwriteConfirmer {
	/**
	 * Return whether the entity generator should overwrite the specified
	 * file.
	 */
	boolean overwrite(String className);


	final class Always
		implements OverwriteConfirmer
	{
		public static final OverwriteConfirmer INSTANCE = new Always();

		public static OverwriteConfirmer instance() {
			return INSTANCE;
		}

		// ensure single instance
		private Always() {
			super();
		}

		// everything will be overwritten
		public boolean overwrite(String className) {
			return true;
		}

		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
	}


	final class Never
		implements OverwriteConfirmer
	{
		public static final OverwriteConfirmer INSTANCE = new Never();

		public static OverwriteConfirmer instance() {
			return INSTANCE;
		}

		// ensure single instance
		private Never() {
			super();
		}

		// nothing will be overwritten
		public boolean overwrite(String className) {
			return false;
		}

		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
	}
}
