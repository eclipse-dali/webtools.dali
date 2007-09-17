package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
//Validation:
// 1. Multiple type mapping annotations (Entity and Embeddable (Entity and Entity handled by compiler))
// 2. type annotations that don't belong with the specific type mapping annotation (Table isn't valid with Embeddable)
// 3. validation for having both singular and plural annotations.  model will only include the plural
public class JavaPersistentTypeResourceImpl extends AbstractJavaResource<Type> implements JavaPersistentTypeResource
{	
	private Collection<JavaTypeAnnotation> javaTypeAnnotations;
	
	private Collection<JavaTypeMappingAnnotation> javaTypeMappingAnnotations;

	public JavaPersistentTypeResourceImpl(Type type, JpaPlatform jpaPlatform){
		super(type, jpaPlatform);
		this.javaTypeAnnotations = new ArrayList<JavaTypeAnnotation>();
		this.javaTypeMappingAnnotations = new ArrayList<JavaTypeMappingAnnotation>();
	}

	protected ListIterator<JavaTypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders() {
		return jpaPlatform().javaTypeMappingAnnotationProviders();
	}


	public JavaTypeAnnotation javaTypeAnnotation(String annotationName) {
		for (Iterator<JavaTypeAnnotation> i = javaTypeAnnotations(); i.hasNext(); ) {
			JavaTypeAnnotation javaTypeAnnotation = i.next();
			if (javaTypeAnnotation.getAnnotationName().equals(annotationName)) {
				return javaTypeAnnotation;
			}
		}
		return null;
	}
	
	public JavaTypeMappingAnnotation javaTypeMappingAnnotation(String annotationName) {
		for (Iterator<JavaTypeMappingAnnotation> i = javaTypeMappingAnnotations(); i.hasNext(); ) {
			JavaTypeMappingAnnotation javaTypeMappingAnnotation = i.next();
			if (javaTypeMappingAnnotation.getAnnotationName().equals(annotationName)) {
				return javaTypeMappingAnnotation;
			}
		}
		return null;
	}


	public Iterator<JavaTypeAnnotation> javaTypeAnnotations() {
		return new CloneIterator<JavaTypeAnnotation>(this.javaTypeAnnotations);
	}

	public JavaTypeAnnotation addJavaTypeAnnotation(String annotationName) {
		JavaTypeAnnotationProvider provider = jpaPlatform().javaTypeAnnotationProvider(annotationName);
		JavaTypeAnnotation javaTypeAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
		addJavaTypeAnnotation(javaTypeAnnotation);		
		javaTypeAnnotation.newAnnotation();
		
		return javaTypeAnnotation;
	}

	public JavaTypeAnnotation addJavaTypeAnnotation(int index, String singularAnnotationName, String pluralAnnotationName) {
		JavaSingularTypeAnnotation singularAnnotation = (JavaSingularTypeAnnotation) javaTypeAnnotation(singularAnnotationName);
		JavaPluralTypeAnnotation pluralAnnotation = (JavaPluralTypeAnnotation) javaTypeAnnotation(pluralAnnotationName);
		
		if (pluralAnnotation != null) {
			//ignore any singularAnnotation and just add to the plural one
			JavaSingularTypeAnnotation singularTypeAnnotation = pluralAnnotation.add(index);
			synchAnnotationsAfterAdd(index + 1, pluralAnnotation);
			singularTypeAnnotation.newAnnotation();
			return singularTypeAnnotation;
		}
		if (singularAnnotation == null) {
			//add the singular since neither singular or plural exists
			return addJavaTypeAnnotation(singularAnnotationName);
		}
		//move the singular to a new plural annotation and add to it
		removeJavaTypeAnnotation(singularAnnotation);
		JavaPluralTypeAnnotation pluralTypeAnnotation = (JavaPluralTypeAnnotation) addJavaTypeAnnotation(pluralAnnotationName);
		JavaSingularTypeAnnotation newSingularAnnotation = pluralTypeAnnotation.add(0);
		newSingularAnnotation.newAnnotation();
		newSingularAnnotation.initializeFrom(singularAnnotation);
		return pluralTypeAnnotation.add(pluralTypeAnnotation.javaTypeAnnotationsSize());
	}
	
	/**
	 * synchronize the annotations with the model singularTypeAnnotations,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAnnotationsAfterAdd(int index, JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralAnnotation) {
		List<JavaSingularTypeAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.javaTypeAnnotations());
		for (int i = singularAnnotations.size(); i-- > index;) {
			this.synch(singularAnnotations.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model singularTypeAnnotations,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAnnotationsAfterRemove(int index, JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralAnnotation) {
		List<JavaSingularTypeAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.javaTypeAnnotations());
		for (int i = index; i < singularAnnotations.size(); i++) {
			this.synch(singularAnnotations.get(i), i);
		}
	}

	private void synch(JavaSingularTypeAnnotation singularTypeAnnotation, int index) {
		singularTypeAnnotation.moveAnnotation(index);
	}

	public void move(int newIndex, JavaSingularTypeAnnotation singularAnnotation, String pluralAnnotationName) {
		JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralTypeAnnotation = (JavaPluralTypeAnnotation<JavaSingularTypeAnnotation>) javaTypeAnnotation(pluralAnnotationName);
		int oldIndex = pluralTypeAnnotation.indexOf(singularAnnotation);
		move(oldIndex, newIndex, pluralTypeAnnotation);
	}
	
	public void move(int oldIndex, int newIndex, String pluralAnnotationName) {
		JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralTypeAnnotation = (JavaPluralTypeAnnotation<JavaSingularTypeAnnotation>) javaTypeAnnotation(pluralAnnotationName);
		move(oldIndex, newIndex, pluralTypeAnnotation);
	}
	
	private void move(int sourceIndex, int targetIndex, JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralAnnotation) {
		pluralAnnotation.move(sourceIndex, targetIndex);
		synchAnnotationsAfterMove(sourceIndex, targetIndex, pluralAnnotation);
	}
	
	/**
	 * synchronize the annotations with the model singularTypeAnnotations
	 */
	private void synchAnnotationsAfterMove(int sourceIndex, int targetIndex, JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralAnnotation) {
		JavaSingularTypeAnnotation singularTypeAnnotation = pluralAnnotation.singularAnnotationAt(targetIndex);
		
		this.synch(singularTypeAnnotation, pluralAnnotation.javaTypeAnnotationsSize());
		
		List<JavaSingularTypeAnnotation> singularAnnotations = CollectionTools.list(pluralAnnotation.javaTypeAnnotations());
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				synch(singularAnnotations.get(i), i);
			}
		}
		else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				synch(singularAnnotations.get(i), i);			
			}
		}
		this.synch(singularTypeAnnotation, targetIndex);
	}
	
	private void addJavaTypeAnnotation(JavaTypeAnnotation javaTypeAnnotation) {
		this.javaTypeAnnotations.add(javaTypeAnnotation);
		//TODO event notification
	}
	
	private void removeJavaTypeAnnotation(JavaTypeAnnotation javaTypeAnnotation) {
		this.javaTypeAnnotations.remove(javaTypeAnnotation);
		//TODO looks odd that we remove the annotation here, but in addJavaTypeannotation(JavaTypeAnnotation) we don't do the same
		javaTypeAnnotation.removeAnnotation();
		//TODO event notification
	}
	
	private void addJavaTypeMappingAnnotation(String annotation) {
		if (javaTypeMappingAnnotation(annotation) != null) {
			return;
		}
		JavaTypeMappingAnnotationProvider provider = jpaPlatform().javaTypeMappingAnnotationProvider(annotation);
		JavaTypeMappingAnnotation javaTypeMappingAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
		addJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
		//TODO should this be done here or should creating the JavaTypeAnnotation do this??
		javaTypeMappingAnnotation.newAnnotation();
	}

	private void addJavaTypeMappingAnnotation(JavaTypeMappingAnnotation annotation) {
		this.javaTypeMappingAnnotations.add(annotation);
		//TODO event notification
	}
	
	private void removeJavaTypeMappingAnnotation(JavaTypeMappingAnnotation annotation) {
		this.javaTypeMappingAnnotations.remove(annotation);
		annotation.removeAnnotation();
		//TODO event notification
	}
	
	public Iterator<JavaTypeMappingAnnotation> javaTypeMappingAnnotations() {
		return new CloneIterator<JavaTypeMappingAnnotation>(this.javaTypeMappingAnnotations);
	}

	public void removeJavaTypeAnnotation(String typeAnnotationName) {
		JavaTypeAnnotation javaTypeAnnotation = javaTypeAnnotation(typeAnnotationName);
		if (javaTypeAnnotation != null) {
			removeJavaTypeAnnotation(javaTypeAnnotation);
		}
	}

	public void removeJavaTypeAnnotation(JavaSingularTypeAnnotation singularAnnotation, String pluralAnnotationName) {
		if (singularAnnotation == javaTypeAnnotation(singularAnnotation.getAnnotationName())) {
			removeJavaTypeAnnotation(singularAnnotation);
		}
		else {
			JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralAnnotation = (JavaPluralTypeAnnotation) javaTypeAnnotation(pluralAnnotationName);
			removeJavaTypeAnnotation(pluralAnnotation.indexOf(singularAnnotation), pluralAnnotation);
		}
	}
	
	public void removeJavaTypeAnnotation(int index, String pluralAnnotationName) {
		JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> pluralAnnotation = (JavaPluralTypeAnnotation) javaTypeAnnotation(pluralAnnotationName);
		removeJavaTypeAnnotation(index, pluralAnnotation);
	}
	
	public void removeJavaTypeAnnotation(int index, JavaPluralTypeAnnotation<JavaSingularTypeAnnotation> javaPluralTypeAnnotation) {
		JavaSingularTypeAnnotation singularTypeAnnotation = javaPluralTypeAnnotation.singularAnnotationAt(index);
		javaPluralTypeAnnotation.remove(index);
		singularTypeAnnotation.removeAnnotation();
		synchAnnotationsAfterRemove(index, javaPluralTypeAnnotation);
		
		if (javaPluralTypeAnnotation.javaTypeAnnotationsSize() == 0) {
			removeJavaTypeAnnotation(javaPluralTypeAnnotation);
		}
		else if (javaPluralTypeAnnotation.javaTypeAnnotationsSize() == 1) {
			JavaSingularTypeAnnotation nestedJavaTypeAnnotation = javaPluralTypeAnnotation.singularAnnotationAt(0);
			removeJavaTypeAnnotation(javaPluralTypeAnnotation);
			JavaSingularTypeAnnotation newJavaTypeAnnotation = (JavaSingularTypeAnnotation) addJavaTypeAnnotation(javaPluralTypeAnnotation.getSingularAnnotationName());
			newJavaTypeAnnotation.initializeFrom(nestedJavaTypeAnnotation);
		}
	}
	
	public void removeJavaTypeMappingAnnotation(String annotationName) {
		JavaTypeMappingAnnotation javaTypeMappingAnnotation = javaTypeMappingAnnotation(annotationName);
		removeJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
	}
	
	public void setJavaTypeMappingAnnotation(String annotationName) {
		JavaTypeMappingAnnotation oldMapping = javaTypeMappingAnnotation();
		if (oldMapping != null) {
			removeUnnecessaryAnnotations(oldMapping.getAnnotationName(), annotationName);
			removeJavaTypeMappingAnnotation(oldMapping.getAnnotationName());
		}
		addJavaTypeMappingAnnotation(annotationName);
	}
	
	private void removeUnnecessaryAnnotations(String oldMappingAnnotation, String newMappingAnnotation) {
		JavaTypeMappingAnnotationProvider oldProvider = jpaPlatform().javaTypeMappingAnnotationProvider(oldMappingAnnotation);
		JavaTypeMappingAnnotationProvider newProvider = jpaPlatform().javaTypeMappingAnnotationProvider(newMappingAnnotation);
		
		Collection<String> annotationsToRemove = CollectionTools.collection(oldProvider.correspondingAnnotationNames());
		CollectionTools.removeAll(annotationsToRemove, newProvider.correspondingAnnotationNames());
		
		for (String annotationName : annotationsToRemove) {
			removeJavaTypeAnnotation(annotationName);
		}
	}
	
	public JavaTypeMappingAnnotation javaTypeMappingAnnotation() {
		for (ListIterator<JavaTypeMappingAnnotationProvider> i = javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			JavaTypeMappingAnnotationProvider provider = i.next();
			for (Iterator<JavaTypeMappingAnnotation> j = javaTypeMappingAnnotations(); j.hasNext(); ) {
				JavaTypeMappingAnnotation javaTypeMappingAnnotation = j.next();
				if (provider.getAnnotationName() == javaTypeMappingAnnotation.getAnnotationName()) {
					return javaTypeMappingAnnotation;
				}
			}
		}
		return null;
	}
	
	public ListIterator<JavaTypeAnnotation> javaTypeAnnotations(String singularAnnotation, String pluralAnnotation) {
		JavaTypeAnnotation javaPluralTypeAnnotation = javaTypeAnnotation(pluralAnnotation);
		if (javaPluralTypeAnnotation != null) {
			return ((JavaPluralTypeAnnotation) javaPluralTypeAnnotation).javaTypeAnnotations();
		}
		return new SingleElementListIterator<JavaTypeAnnotation>(javaTypeAnnotation(singularAnnotation));
	}
	
	
	public void updateFromJava(CompilationUnit astRoot) {
		updateJavaTypeAnnotations(astRoot);
		updateJavaTypeMappingAnnotations(astRoot);
	}
	
	private void updateJavaTypeAnnotations(CompilationUnit astRoot) {
		astRoot.accept(getJavaTypeAnnotationAstVisitor());
		ITypeBinding typeBinding = ASTTools.typeBinding(getMember().getFullyQualifiedName(), astRoot);
		for (Iterator<JavaTypeAnnotation> i = javaTypeAnnotations(); i.hasNext(); ) {
			JavaTypeAnnotation javaTypeAnnotation = i.next();
			if (ASTTools.containsAnnotation(javaTypeAnnotation.getAnnotationName(), typeBinding)) {
				removeJavaTypeAnnotation(javaTypeAnnotation);
			}
		}		
	}
	
//	private void updateJavaTypeAnnotations(CompilationUnit astRoot) {
//		for (Iterator<JavaTypeAnnotationProvider> i = jpaPlatform().javaTypeAnnotationProviders(); i.hasNext(); ) {
//			JavaTypeAnnotationProvider provider = i.next();
//			if (getMember().containsAnnotation(provider.getDeclarationAnnotationAdapter(), astRoot)) {
//				JavaTypeAnnotation javaTypeAnnotation = javaTypeAnnotation(provider.getAnnotationName());
//				if (javaTypeAnnotation == null) {
//					javaTypeAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
//					addJavaTypeAnnotation(javaTypeAnnotation);
//				}
//				javaTypeAnnotation.updateFromJava(astRoot);
//			}
//		}
//		
//		for (Iterator<JavaTypeAnnotation> i = javaTypeAnnotations(); i.hasNext(); ) {
//			JavaTypeAnnotation javaTypeAnnotation = i.next();
//			if (!getMember().containsAnnotation(javaTypeAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
//				removeJavaTypeAnnotation(javaTypeAnnotation);
//			}
//		}		
//	}
	
	private void updateJavaTypeMappingAnnotations(CompilationUnit astRoot) {
		astRoot.accept(getJavaTypeMappingAnnotationAstVisitor());
		
		for (Iterator<JavaTypeMappingAnnotation> i = javaTypeMappingAnnotations(); i.hasNext(); ) {
			JavaTypeMappingAnnotation javaTypeMappingAnnotation = i.next();
			if (!getMember().containsAnnotation(javaTypeMappingAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
				removeJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
			}
		}
	}
	
//	private void updateJavaTypeMappingAnnotations(CompilationUnit astRoot) {
//		for (Iterator<JavaTypeMappingAnnotationProvider> i = jpaPlatform().javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
//			JavaTypeMappingAnnotationProvider provider = i.next();
//			if (getMember().containsAnnotation(provider.getDeclarationAnnotationAdapter(), astRoot)) {
//				JavaTypeMappingAnnotation javaTypeMappingAnnotation = javaTypeMappingAnnotation(provider.getAnnotationName());
//				if (javaTypeMappingAnnotation == null) {
//					javaTypeMappingAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
//					addJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
//				}
//				javaTypeMappingAnnotation.updateFromJava(astRoot);
//			}
//		}
//		
//		for (Iterator<JavaTypeMappingAnnotation> i = javaTypeMappingAnnotations(); i.hasNext(); ) {
//			JavaTypeMappingAnnotation javaTypeMappingAnnotation = i.next();
//			if (!getMember().containsAnnotation(javaTypeMappingAnnotation.getDeclarationAnnotationAdapter(), astRoot)) {
//				removeJavaTypeMappingAnnotation(javaTypeMappingAnnotation);
//			}
//		}
//	}
	private ASTVisitor typeVisitor;

	private ASTVisitor getJavaTypeAnnotationAstVisitor() {
		if (this.typeVisitor == null) {
			this.typeVisitor =  new ASTVisitor() {
//				@Override
//				public boolean visit(TypeDeclaration node) {
//					if (getMember().b.equals(node.)
//					return super.visit(node);
//				}
				@Override
				public boolean visit(SingleMemberAnnotation node) {
					return visit((Annotation) node);
				}
			
				@Override
				public boolean visit(NormalAnnotation node) {
					return visit((Annotation) node);
				}
			
				@Override
				public boolean visit(MarkerAnnotation node) {
					return visit((Annotation) node);
				}
				
				private boolean visit(Annotation node) {
					if (node.getParent().getNodeType() != ASTNode.TYPE_DECLARATION) {
						return false;
					}
					String qualifiedAnnotationName = qualifiedAnnotationName(node);
					if (qualifiedAnnotationName == null) {
						return false;
					}
					JavaTypeAnnotationProvider provider = jpaPlatform().javaTypeAnnotationProvider(qualifiedAnnotationName);
					if (provider != null) {
						JavaTypeAnnotation javaTypeAnnotation = javaTypeAnnotation(qualifiedAnnotationName);
						if (javaTypeAnnotation == null) {
							javaTypeAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
							addJavaTypeAnnotation(javaTypeAnnotation);				
						}
						//TODO is it a bad idea to cast here??
						javaTypeAnnotation.updateFromJava((CompilationUnit) node.getRoot());
					}
					return false;
				}
			};
		}
		return this.typeVisitor;
	}
	
	private static String qualifiedAnnotationName(Annotation node) {
		IAnnotationBinding annotationBinding = node.resolveAnnotationBinding();
		if (annotationBinding == null) {
			return null;
		}
		ITypeBinding annotationTypeBinding = annotationBinding.getAnnotationType();
		if (annotationTypeBinding == null) {
			return null;
		}
		return annotationTypeBinding.getQualifiedName();
	}
	
	private ASTVisitor typeMappingVisitor;
	private ASTVisitor getJavaTypeMappingAnnotationAstVisitor() {
		if (this.typeMappingVisitor == null) {
			this.typeMappingVisitor =  new ASTVisitor() {
				@Override
				public boolean visit(SingleMemberAnnotation node) {
					return visit((Annotation) node);
				}
			
				@Override
				public boolean visit(NormalAnnotation node) {
					return visit((Annotation) node);
				}
			
				@Override
				public boolean visit(MarkerAnnotation node) {
					return visit((Annotation) node);
				}
				
				private boolean visit(Annotation node) {
					if (node.getParent().getNodeType() != ASTNode.TYPE_DECLARATION) {
						return false;
					}
					String qualifiedAnnotationName = qualifiedAnnotationName(node);
					if (qualifiedAnnotationName == null) {
						return false;
					}
					JavaTypeMappingAnnotationProvider provider = jpaPlatform().javaTypeMappingAnnotationProvider(qualifiedAnnotationName);
					if (provider != null) {
						JavaTypeMappingAnnotation javaTypeMappingAnnotation = javaTypeMappingAnnotation(qualifiedAnnotationName);
						if (javaTypeMappingAnnotation == null) {
							javaTypeMappingAnnotation = provider.buildJavaTypeAnnotation(getMember(), jpaPlatform());
							addJavaTypeMappingAnnotation(javaTypeMappingAnnotation);				
						}
						//TODO is it a bad idea to cast here??
						javaTypeMappingAnnotation.updateFromJava((CompilationUnit) node.getRoot());
					}
					return false;
				}
			};
		}
		return this.typeMappingVisitor;
	}
	

}
