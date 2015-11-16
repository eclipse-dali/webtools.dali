/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.reference.ModifiableBooleanReference;

public abstract class ModifiableBooleanReferenceTests
	extends BooleanReferenceTests
{
	public ModifiableBooleanReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableBooleanReference buildBooleanReference() {
		return (ModifiableBooleanReference) super.buildBooleanReference();
	}

	@Override
	protected abstract ModifiableBooleanReference buildBooleanReference(boolean value);

	public void testSetValue() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.getValue());
		br.setValue(false);
		assertFalse(br.getValue());
	}

	public void testFlip() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.getValue());
		assertFalse(br.flip());
		assertFalse(br.getValue());
		assertTrue(br.flip());
		assertTrue(br.getValue());
	}

	public void testAnd() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.and(true));
		assertTrue(br.getValue());

		assertFalse(br.and(false));
		assertFalse(br.getValue());

		assertFalse(br.and(true));
		assertFalse(br.getValue());

		assertFalse(br.and(false));
		assertFalse(br.getValue());
	}

	public void testOr() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.or(true));
		assertTrue(br.getValue());

		assertTrue(br.or(false));
		assertTrue(br.getValue());

		assertTrue(br.setValue(false));
		assertFalse(br.or(false));
		assertFalse(br.getValue());

		assertTrue(br.or(true));
		assertTrue(br.getValue());
	}

	public void testXor() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertFalse(br.xor(true));
		assertFalse(br.getValue());

		assertFalse(br.setValue(true));
		assertTrue(br.xor(false));
		assertTrue(br.getValue());

		assertTrue(br.setValue(false));
		assertFalse(br.xor(false));
		assertFalse(br.getValue());

		assertFalse(br.setValue(false));
		assertTrue(br.xor(true));
		assertTrue(br.getValue());
	}

	public void testSetNotBoolean() {
		ModifiableBooleanReference br = this.buildBooleanReference(false);
		assertFalse(br.getValue());
		br.setNot(true);
		assertFalse(br.getValue());
		br.setNot(true);
		assertFalse(br.getValue());
		br.setNot(false);
		assertTrue(br.getValue());
	}

	public void testSetTrue() {
		ModifiableBooleanReference br = this.buildBooleanReference(false);
		assertFalse(br.getValue());
		br.setTrue();
		assertTrue(br.getValue());
	}

	public void testSetFalse() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.getValue());
		br.setFalse();
		assertFalse(br.getValue());
	}

	public void testCommit() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.commit(false, true));
		assertFalse(br.getValue());

		assertFalse(br.commit(false, true));
		assertFalse(br.getValue());

		assertTrue(br.commit(true, false));
		assertTrue(br.getValue());

		assertFalse(br.commit(true, false));
		assertTrue(br.getValue());

		assertFalse(br.commit(false, false));
		assertTrue(br.getValue());

		assertTrue(br.setValue(false));
		assertFalse(br.commit(true, true));
		assertFalse(br.getValue());
	}

	public void testSwap_sameObject() {
		ModifiableBooleanReference br = this.buildBooleanReference(true);
		assertTrue(br.swap(br));
		assertTrue(br.getValue());
	}

	public void testSwap_sameValues() {
		ModifiableBooleanReference br1 = this.buildBooleanReference(true);
		ModifiableBooleanReference br2 = this.buildBooleanReference(true);
		assertTrue(br1.swap(br2));
		assertTrue(br1.getValue());
		assertTrue(br2.getValue());
	}

	public void testSwap_differentValues1() {
		ModifiableBooleanReference br1 = this.buildBooleanReference(true);
		ModifiableBooleanReference br2 = this.buildBooleanReference(false);
		assertFalse(br1.swap(br2));
		assertFalse(br1.getValue());
		assertTrue(br2.getValue());
	}

	public void testSwap_differentValues2() {
		ModifiableBooleanReference br1 = this.buildBooleanReference(false);
		ModifiableBooleanReference br2 = this.buildBooleanReference(true);
		assertTrue(br1.swap(br2));
		assertTrue(br1.getValue());
		assertFalse(br2.getValue());
	}
}
