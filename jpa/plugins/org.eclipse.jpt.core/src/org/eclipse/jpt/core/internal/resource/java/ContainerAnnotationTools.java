/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class ContainerAnnotationTools
{
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
	public static void synchAnnotationsAfterMove(int sourceIndex, int targetIndex, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
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
	
	public static void updateNestedAnnotationsFromJava(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		addOrUpdateAnnotationInSource(astRoot, containerAnnotation);
		removeAnnotationsNotInSource(astRoot, containerAnnotation);
	}

	private static void addOrUpdateAnnotationInSource(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		containerAnnotation.jdtAnnotation(astRoot).accept(javaMemberAnnotationAstVisitor(astRoot, containerAnnotation));
	}
	
	private static void removeAnnotationsNotInSource(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		for (NestableAnnotation annotation : CollectionTools.iterable(containerAnnotation.nestedAnnotations())) {
			if (annotation.jdtAnnotation(astRoot) == null) {
				containerAnnotation.remove(annotation);
			}
		}		
	}
	
	private static ASTVisitor javaMemberAnnotationAstVisitor(final CompilationUnit astRoot, final ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
		return new ASTVisitor() {
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
					if (nestedAnnotation == null) {
						nestedAnnotation = containerAnnotation.add(containerAnnotation.nestedAnnotationsSize());
					}
					nestedAnnotation.updateFromJava(astRoot);
				}
				return false;
			}
		};
	}
}
