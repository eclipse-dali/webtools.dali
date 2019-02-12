/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.translators;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Root translator that contains a list of child translators and no special
 * behavior.
 */
public class SimpleRootTranslator
	extends RootTranslator
{
	protected Translator[] children;

	public SimpleRootTranslator(String domPathAndNames, EClass eClass) {
		super(domPathAndNames, eClass);
	}

	public SimpleRootTranslator(String domPathAndNames, EClass eClass, Translator[] children) {
		super(domPathAndNames, eClass);
		this.children = children;
	}

	/**
	 * Widen method access to 'public'.
	 */
	@Override
	public Translator[] getChildren() {
		return this.children;
	}

	protected Translator[] getChildren_() {
		return (this.children == null) ? EMPTY_TRANSLATOR_ARRAY : this.children;
	}
	protected static final Translator[] EMPTY_TRANSLATOR_ARRAY = new Translator[0];

	/**
	 * Set the translator's children.
	 * Return the translator.
	 */
	public void setChildren(Translator[] children) {
		this.children = children;
	}

	/**
	 * Add the specified translator to the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleRootTranslator addChild(Translator translator) {
		this.children = ArrayTools.add(this.getChildren_(), translator);
		return this;
	}

	/**
	 * Add the specified translators to the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleRootTranslator addChildren(Translator[] translators) {
		this.children = ArrayTools.addAll(this.getChildren_(), translators);
		return this;
	}

	/**
	 * Remove the specified translator from the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleRootTranslator removeChild(Translator translator) {
		this.children = ArrayTools.remove(this.children, translator);
		return this;
	}

	/**
	 * Remove the specified translators from the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleRootTranslator removeChildren(Translator[] translators) {
		this.children = ArrayTools.removeAll(this.children, (Object[]) translators);
		return this;
	}

}
