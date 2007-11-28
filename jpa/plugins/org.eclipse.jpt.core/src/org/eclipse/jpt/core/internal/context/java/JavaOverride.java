/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.resource.java.OverrideResource;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public abstract class JavaOverride<T extends OverrideResource> extends JavaContextModel implements IOverride
{

	protected String name;

	protected final Owner owner;

	protected T overrideResource;
	
	public JavaOverride(IJavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
	}
	
	public void initializeFromResource(T overrideResource) {
		this.overrideResource = overrideResource;
		this.name = this.name(overrideResource);
	}

	protected T getOverrideResource() {
		return this.overrideResource;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		if (!this.isVirtual()) {
			this.overrideResource.setName(newName);
		}
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public void update(T overrideResource) {
		this.overrideResource = overrideResource;
		this.setName(this.name(overrideResource));
	}

	protected String name(OverrideResource overrideResource) {
		return overrideResource.getName();
	}

	public boolean isVirtual() {
		return owner().isVirtual(this);
	}

	public Owner owner() {
		return this.owner;
	}
	
	@Override
	public IJavaJpaContextNode parent() {
		return (IJavaJpaContextNode) super.parent();
	}

	protected abstract Iterator<String> candidateNames();

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateNames(), filter);
	}

	private Iterator<String> quotedCandidateNames(Filter<String> filter) {
		return StringTools.quote(this.candidateNames(filter));
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.quotedCandidateNames(filter);
		}
		return null;
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.overrideResource.nameTouches(pos, astRoot);
	}
	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		ITextRange textRange = this.overrideResource.textRange(astRoot);
		return (textRange != null) ? textRange : this.parent().validationTextRange(astRoot);
	}

}