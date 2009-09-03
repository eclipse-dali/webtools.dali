/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

public interface OverwriteConfirmer {
	/**
	 * Return whether the entity generator should overwrite the specified
	 * file.
	 */
	boolean overwrite(String className);


	final class Always implements OverwriteConfirmer {
		public static final OverwriteConfirmer INSTANCE = new Always();
		public static OverwriteConfirmer instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Always() {
			super();
		}
		// everything will be overwritten
		public boolean overwrite(String arg0) {
			return true;
		}
		@Override
		public String toString() {
			return "OverwriteConfirmer.Always";  //$NON-NLS-1$
		}
	}


	final class Never implements OverwriteConfirmer {
		public static final OverwriteConfirmer INSTANCE = new Never();
		public static OverwriteConfirmer instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Never() {
			super();
		}
		// nothing will be overwritten
		public boolean overwrite(String arg0) {
			return false;
		}
		@Override
		public String toString() {
			return "OverwriteConfirmer.Never";  //$NON-NLS-1$
		}
	}

}