/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Binary convenience methods
 */
// TODO hopefully this class can go away with some sort of refactoring of the
// source and binary hierarchies...
public abstract class BinaryNode
	extends AbstractJavaResourceNode
{
	protected BinaryNode(JavaResourceNode parent) {
		super(parent);
	}


	// ********** JavaResourceNode implementation **********

	@Override
	public IFile getFile() {
		return null;  // only BinaryPackageFragmentRoot has a file...
	}

	public void update() {
		// nothing by default
	}

	public JavaResourceCompilationUnit getJavaResourceCompilationUnit() {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void initialize(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}


	// ********** member value-pairs **********

	/**
	 * Return the <em>values</em> of the specified JDT annotation's member with
	 * the specified name.
	 */
	public Object[] getJdtMemberValues(IAnnotation jdtContainerAnnotation, String memberName) {
		Object[] values = (Object[]) this.getJdtMemberValue(jdtContainerAnnotation, memberName);
		return (values != null) ? values : EMPTY_OBJECT_ARRAY;
	}
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/**
	 * Return the value of the specified JDT annotation's member with
	 * the specified name.
	 */
	public Object getJdtMemberValue(IAnnotation jdtContainerAnnotation, String memberName) {
		IMemberValuePair pair = this.getJdtMemberValuePair(jdtContainerAnnotation, memberName);
		return (pair == null) ? null : pair.getValue();
	}

	/**
	 * Return the specified JDT annotation's member-value pair with
	 * the specified name.
	 */
	public IMemberValuePair getJdtMemberValuePair(IAnnotation jdtContainerAnnotation, String memberName) {
		for (IMemberValuePair pair : this.getJdtMemberValuePairs(jdtContainerAnnotation)) {
			if (pair.getMemberName().equals(memberName)) {
				return pair;
			}
		}
		return null;
	}

	public IMemberValuePair[] getJdtMemberValuePairs(IAnnotation jdtContainerAnnotation) {
		try {
			return jdtContainerAnnotation.getMemberValuePairs();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EMPTY_MEMBER_VALUE_PAIR_ARRAY;
		}
	}
	private static final IMemberValuePair[] EMPTY_MEMBER_VALUE_PAIR_ARRAY = new IMemberValuePair[0];
}
