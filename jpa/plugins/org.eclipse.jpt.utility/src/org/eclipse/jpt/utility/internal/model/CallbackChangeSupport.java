/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import org.eclipse.jpt.utility.model.Model;

/**
 * This change support class will notify the source when one of the source's
 * aspects has changed.
 */
public class CallbackChangeSupport extends ChangeSupport {
	private static final long serialVersionUID = 1L;

	public CallbackChangeSupport(Source source) {
		super(source);
	}

	protected Source source() {
		return (Source) this.source;
	}

	@Override
	protected ChangeSupport buildChildChangeSupport() {
		return new Child(this.source());
	}

	@Override
	protected void sourceChanged(String aspectName) {
		super.sourceChanged(aspectName);
		this.source().aspectChanged(aspectName);
	}


	// ********** child change support **********

	/**
	 * The aspect-specific change support class does not need to
	 * notify the source node of changes (the parent will take care of that);
	 * nor does it need to build "grandchildren" change support objects.
	 */
	protected static class Child extends ChangeSupport {
		private static final long serialVersionUID = 1L;

		public Child(Source source) {
			super(source);
		}

		protected Source source() {
			return (Source) this.source;
		}

		@Override
		protected ChangeSupport buildChildChangeSupport() {
			// there are no grandchildren
			throw new UnsupportedOperationException();
		}

	}


	// ********** source interface **********

	/**
	 * The callback change support source must implement this interface so it
	 * can be notified of any aspect changes.
	 */
	public interface Source extends Model {

		/**
		 * The specified aspect changed.
		 */
		void aspectChanged(String aspectName);

	}

}
