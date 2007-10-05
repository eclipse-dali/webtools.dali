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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class ContainerAnnotationTools
{
	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	public static void synchAnnotationsAfterAdd(int index, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
		for (int i = nestableAnnotations.size(); i-- > index;) {
			synch(nestableAnnotations.get(i), i);
		}
	}
	
	/**
	 * synchronize the source annotations with the model nestableAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	public static void synchAnnotationsAfterRemove(int index, ContainerAnnotation<NestableAnnotation> pluralAnnotation) {
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(pluralAnnotation.nestedAnnotations());
		for (int i = index; i < nestableAnnotations.size(); i++) {
			synch(nestableAnnotations.get(i), i);
		}
	}
	
	private static void synch(NestableAnnotation nestableAnnotation, int index) {
		nestableAnnotation.moveAnnotation(index);
	}
	
	public static void move(int sourceIndex, int targetIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
		containerAnnotation.move(sourceIndex, targetIndex);
		synchAnnotationsAfterMove(sourceIndex, targetIndex, containerAnnotation);
	}
	/**
	 * synchronize the annotations with the model nestableAnnotations
	 */
	public static void synchAnnotationsAfterMove(int sourceIndex, int targetIndex, ContainerAnnotation<NestableAnnotation> containerAnnotation) {
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
	
	//TODO
//	public static void updateNestedAnnotationsFromJava(CompilationUnit astRoot, ContainerAnnotation<? extends NestableAnnotation> containerAnnotation) {
//		List<NestableAnnotation> nestedAnnotations = CollectionTools.list(containerAnnotation.nestedAnnotations());
//		int persSize = nestedAnnotations.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			NestableAnnotation nestedAnnotation = nestedAnnotations.get(i);
//			if (nestedAnnotation.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			nestedAnnotation.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model secondary tables beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				nestedAnnotations.remove(persSize);
//			}
//		}
//		else {
//			// add new model secondary tables until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				NestableAnnotation nestedAnnotation = containerAnnotation.createNestedAnnotation(javaSize);
//				if (nestedAnnotation.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					containerAnnotation.add(containerAnnotation.nestedAnnotationsSize(), nestedAnnotation);
//					nestedAnnotation.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}


}
