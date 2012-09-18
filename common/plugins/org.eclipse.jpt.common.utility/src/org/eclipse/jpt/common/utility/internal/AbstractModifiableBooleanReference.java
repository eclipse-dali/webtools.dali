/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

/**
 * Convenience abstract class for modifiable boolean reference implementations.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * <li>{@link #setValue(boolean)}
 * </ul>
 */
public abstract class AbstractModifiableBooleanReference
	extends AbstractBooleanReference
	implements ModifiableBooleanReference
{
	protected AbstractModifiableBooleanReference() {
		super();
	}

	public boolean flip() {
		return this.setValue( ! this.getValue());
	}

	public boolean setNot(boolean value) {
		return this.setValue( ! value);
	}

	public boolean setTrue() {
		return this.setValue(true);
	}

	public boolean setFalse() {
		return this.setValue(false);
	}
}
