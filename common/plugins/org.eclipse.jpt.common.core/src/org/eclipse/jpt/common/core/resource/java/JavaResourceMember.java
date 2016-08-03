/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Java source code or binary persistent member.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface JavaResourceMember
	extends JavaResourceAnnotatedElement
{
	String getName();
	Transformer<JavaResourceMember, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<JavaResourceMember, String>
	{
		@Override
		public String transform(JavaResourceMember member) {
			return member.getName();
		}
	}

	// ********** annotations **********
	
	/**
	 * Sets the specified primary annotation as the first annotation, and removes all known 
	 * annotations (i.e. does not remove non-persistence annotations) which are not included
	 * in the supporting annotations.
	 */
	Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames);



	// ********** modifiers **********

	/**
	 * Return whether the member is final.
	 */
	boolean isFinal();
		String FINAL_PROPERTY = "final"; //$NON-NLS-1$
	Predicate<JavaResourceMember> FINAL_PREDICATE = new FinalPredicate();
	class FinalPredicate
		extends PredicateAdapter<JavaResourceMember>
	{
		@Override
		public boolean evaluate(JavaResourceMember member) {
			return member.isFinal();
		}
	}

	boolean isTransient();
		String TRANSIENT_PROPERTY = "transient"; //$NON-NLS-1$
	Predicate<JavaResourceMember> TRANSIENT_PREDICATE = new TransientPredicate();
	class TransientPredicate
		extends PredicateAdapter<JavaResourceMember>
	{
		@Override
		public boolean evaluate(JavaResourceMember member) {
			return member.isTransient();
		}
	}

	boolean isPublic();
		String PUBLIC_PROPERTY = "public"; //$NON-NLS-1$
	Predicate<JavaResourceMember> PUBLIC_PREDICATE = new PublicPredicate();
	class PublicPredicate
		extends PredicateAdapter<JavaResourceMember>
	{
		@Override
		public boolean evaluate(JavaResourceMember member) {
			return member.isPublic();
		}
	}

	boolean isStatic();
		String STATIC_PROPERTY = "static"; //$NON-NLS-1$
	Predicate<JavaResourceMember> STATIC_PREDICATE = new StaticPredicate();
	class StaticPredicate
		extends PredicateAdapter<JavaResourceMember>
	{
		@Override
		public boolean evaluate(JavaResourceMember member) {
			return member.isStatic();
		}
	}

	boolean isProtected();
		String PROTECTED_PROPERTY = "protected"; //$NON-NLS-1$
	Predicate<JavaResourceMember> PROTECTED_PREDICATE = new ProtectedPredicate();
	class ProtectedPredicate
		extends PredicateAdapter<JavaResourceMember>
	{
		@Override
		public boolean evaluate(JavaResourceMember member) {
			return member.isProtected();
		}
	}


	// ********** queries **********

	/**
	 * Return whether the Java resource member is for the specified
	 * member.
	 */
	boolean isFor(String memberName, int occurrence);


	// ********** behavior **********
	
	/**
	 * Return whether the Java resource member is public or protected
	 */
	boolean isPublicOrProtected();
}
