/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class ContainerAnnotationTools
{
	
	public static NestableAnnotation addNestedAnnotation(int index, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		int size = containerAnnotation.nestedAnnotationsSize();
		NestableAnnotation nestedAnnotation = containerAnnotation.addInternal(size);
		nestedAnnotation.newAnnotation();
		containerAnnotation.moveInternal(index, size);
		synchAnnotationsAfterMove(index, size, containerAnnotation);
		return nestedAnnotation;
	}

	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	public static void synchAnnotationsAfterAdd(int index, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
		for (int i = nestableAnnotations.size(); i-- > index;) {
			synch(nestableAnnotations.get(i), i);
		}
	}
	
	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	public static void synchAnnotationsAfterRemove(int index, ContainerAnnotation<? extends NestableAnnotation> pluralAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(pluralAnnotation.nestedAnnotations());
		for (int i = index; i < nestableAnnotations.size(); i++) {
			synch(nestableAnnotations.get(i), i);
		}
	}
	
	private static void synch(NestableAnnotation nestableAnnotation, int index) {
		nestableAnnotation.moveAnnotation(index);
	}
	
	/**
	 * synchronize the annotations with the model nestableAnnotations
	 */
	public static void synchAnnotationsAfterMove(int targetIndex, int sourceIndex, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		NestableAnnotation nestableAnnotation = containerAnnotation.nestedAnnotationAt(targetIndex);
		
		synch(nestableAnnotation, containerAnnotation.nestedAnnotationsSize());
		
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				synch(nestableAnnotations.get(i), i);
			}
		}
		else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				synch(nestableAnnotations.get(i), i);			
			}
		}
		synch(nestableAnnotation, targetIndex);
	}
	
	
	public static void initializeNestedAnnotations(CompilationUnit astRoot, ContainerAnnotation<?> containerAnnotation) {
		addAnnotationsFromSource(astRoot, containerAnnotation);
	}	
	
	private static void addAnnotationsFromSource(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		containerAnnotation.getJdtAnnotation(astRoot).accept(initialJavaMemberAnnotationAstVisitor(astRoot, containerAnnotation));
	}
	
	/**
	 * Only visit the member value pair with the given element name.  
	 * If there is no element name (like in the case of value elements)
	 * then we will visit all annotations with the annotation name inside
	 * the given container annotation
	 */
	private static ASTVisitor initialJavaMemberAnnotationAstVisitor(final CompilationUnit astRoot, final ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		return new ASTVisitor() {
			@Override
			public boolean visit(MemberValuePair node) {
				return node.getName().getFullyQualifiedName().equals(containerAnnotation.getElementName());
			}
			@Override
			public boolean visit(SingleMemberAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(NormalAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(MarkerAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
			
			private boolean visit(org.eclipse.jdt.core.dom.Annotation node) {
				if (containerAnnotation.getAnnotationName().equals(JDTTools.resolveAnnotation(node))) {
					return true;
				}
				if (containerAnnotation.getNestableAnnotationName().equals(JDTTools.resolveAnnotation(node))) {
					Annotation nestedAnnotation = containerAnnotation.addInternal(containerAnnotation.nestedAnnotationsSize());
					nestedAnnotation.initialize(astRoot);
				}
				return false;
			}
		};
	}
	
	public static void updateNestedAnnotationsFromJava(CompilationUnit astRoot, ContainerAnnotation<?> containerAnnotation) {
		addOrUpdateAnnotationInSource(astRoot, containerAnnotation);
		//TODO not sure how to handle generics here and get rid of this warning
		removeAnnotationsNotInSource(astRoot, (ContainerAnnotation<NestableAnnotation>) containerAnnotation);
	}

	private static void addOrUpdateAnnotationInSource(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		containerAnnotation.getJdtAnnotation(astRoot).accept(javaMemberAnnotationAstVisitor(astRoot, containerAnnotation));
	}
	
	private static void removeAnnotationsNotInSource(CompilationUnit astRoot, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		for (NestableAnnotation annotation : CollectionTools.iterable(containerAnnotation.nestedAnnotations())) {
			if (annotation.getJdtAnnotation(astRoot) == null) {
				containerAnnotation.remove(annotation);
			}
		}		
	}
	
	/**
	 * Only visit the member value pair with the given element name.  
	 * If there is no element name (like in the case of value elements)
	 * then we will visit all annotations with the annotation name inside
	 * the given container annotation
	 */
	private static ASTVisitor javaMemberAnnotationAstVisitor(final CompilationUnit astRoot, final ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		return new ASTVisitor() {
			@Override
			public boolean visit(MemberValuePair node) {
				return node.getName().getFullyQualifiedName().equals(containerAnnotation.getElementName());
			}
			
			@Override
			public boolean visit(SingleMemberAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(NormalAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
		
			@Override
			public boolean visit(MarkerAnnotation node) {
				return visit((org.eclipse.jdt.core.dom.Annotation) node);
			}
			
			private boolean visit(org.eclipse.jdt.core.dom.Annotation node) {
				if (containerAnnotation.getAnnotationName().equals(JDTTools.resolveAnnotation(node))) {
					return true;
				}
				if (containerAnnotation.getNestableAnnotationName().equals(JDTTools.resolveAnnotation(node))) {
					NestableAnnotation nestedAnnotation = containerAnnotation.nestedAnnotationFor(node);
					if (nestedAnnotation != null) {
						nestedAnnotation.update(astRoot);
					}
					else {
						nestedAnnotation = containerAnnotation.add(containerAnnotation.nestedAnnotationsSize());
						nestedAnnotation.initialize(astRoot);
					}
				}
				return false;
			}
		};
	}
}
