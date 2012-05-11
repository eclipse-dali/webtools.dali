/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaOverride;
import org.eclipse.jpt.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public abstract class AbstractJavaOverride
	extends AbstractJavaJpaContextNode
	implements JavaOverride
{

	protected String name;

	protected final Owner owner;

	protected OverrideAnnotation overrideAnnotation;
	
	public AbstractJavaOverride(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
	}
	
	protected void initialize(OverrideAnnotation overrideAnnotation) {
		this.overrideAnnotation = overrideAnnotation;
		this.initializeName();
	}

	protected void update(OverrideAnnotation overrideResource) {
		this.overrideAnnotation = overrideResource;
		this.updateName();
	}

	public OverrideAnnotation getOverrideAnnotation() {
		return this.overrideAnnotation;
	}
	
	// ********************* name ****************
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String prefix = getOwner().getPossiblePrefix();
		String unprefixedName = newName;
		if (newName != null && prefix != null && newName.startsWith(prefix)) {
			unprefixedName = newName.substring(newName.indexOf('.') + 1);
		}
		String oldName = this.name;
		this.name = unprefixedName; //set the name without the prefix in the context model
		this.overrideAnnotation.setName(newName); // set the name with the prefix in the resource model
		firePropertyChanged(NAME_PROPERTY, oldName, unprefixedName);
	}
	
	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void initializeName() {
		String name = this.getResourceName();
		String prefix = getOwner().getPossiblePrefix();
		if (name != null && prefix != null && name.startsWith(prefix)) {
			name = name.substring(name.indexOf('.') + 1);
		}
		this.name = name;
	}

	protected void updateName() {
		String name = this.getResourceName();
		String prefix = getOwner().getPossiblePrefix();
		if (name != null && prefix != null && name.startsWith(prefix)) {
			name = name.substring(name.indexOf('.') + 1);
		}
		this.setName_(name);
	}

	protected String getResourceName() {
		return this.overrideAnnotation.getName();
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}

	public BaseOverride setVirtual(boolean virtual) {
		return getOwner().setVirtual(virtual, this);
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}

	protected abstract Iterator<String> candidateNames();

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateNames(), filter);
	}

	private Iterator<String> javaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateNames(filter));
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.javaCandidateNames(filter);
		}
		return null;
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.overrideAnnotation.nameTouches(pos, astRoot);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.overrideAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}

}