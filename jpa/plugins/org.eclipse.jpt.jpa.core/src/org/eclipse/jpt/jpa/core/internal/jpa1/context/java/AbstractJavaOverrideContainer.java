/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import java.util.Vector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java override container
 * <p>
 * <strong>NB:</strong> Although not typical, a Java override container can
 * correspond to only a subset of the annotations in the Java source code.
 * In that case, the index of a specified override must be translated to its
 * corresponding annotation's index before we manipulate the annotations.
 * <p>
 * As of JPA 2.0, the only place we need these translations is for the attribute
 * overrides for an embedded collection. If an embedded collection is a
 * {@link java.util.Map Map}, the attribute overrides can override the mappings
 * for either the map's keys or the map's values. The names of the overrides for
 * the map's keys are prefixed with <code>"key."</code> while the overrides
 * for the map's values are prefixed with <code>"value."</code>.
 * (Just a bit of hack, courtesy of the JPA spec committee.)
 */
public abstract class AbstractJavaOverrideContainer<
			PA extends JavaOverrideContainer.ParentAdapter,
			R extends Override_,
			S extends JavaSpecifiedOverride,
			V extends JavaVirtualOverride,
			A extends OverrideAnnotation & NestableAnnotation
		>
	extends AbstractJavaContextModel<JpaContextModel>
	implements JavaOverrideContainer2_0
{
	/**
	 * this can be <code>null</code> if the container is "read-only"
	 * (i.e. a <em>null</em> container)
	 * @see #getParentAdapter2_0()
	 */
	protected final PA parentAdapter;

	protected final Vector<S> specifiedOverrides = new Vector<S>();
	protected final SpecifiedOverrideContainerAdapter specifiedOverrideContainerAdapter = new SpecifiedOverrideContainerAdapter();

	protected final Vector<V> virtualOverrides = new Vector<V>();
	protected final VirtualOverrideContainerAdapter virtualOverrideContainerAdapter = new VirtualOverrideContainerAdapter();


	protected AbstractJavaOverrideContainer(JpaContextModel parent) {
		super(parent);
		this.parentAdapter = null;
	}

	protected AbstractJavaOverrideContainer(PA parentAdapter) {
		super(parentAdapter.getOverrideContainerParent());
		this.parentAdapter = parentAdapter;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.synchronizeModelsWithResourceModel(this.getSpecifiedOverrides());
		// the virtual overrides do not need a sync
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateSpecifiedOverrides(monitor);
		this.updateVirtualOverrides(monitor);
	}


	// ********** overrides **********

	@SuppressWarnings("unchecked")
	public ListIterable<R> getOverrides() {
		return IterableTools.concatenate(this.getReadOnlySpecifiedOverrides(), this.getReadOnlyVirtualOverrides());
	}

	public int getOverridesSize() {
		return this.specifiedOverrides.size() + this.virtualOverrides.size();
	}

	public R getOverrideNamed(String name) {
		return this.selectOverrideNamed(this.getOverrides(), name);
	}

	public Iterable<String> getOverrideNames() {
		return IterableTools.removeNulls(IterableTools.transform(this.getOverrides(), Override_.NAME_TRANSFORMER));
	}


	// ********** override conversions **********

	/**
	 * <em>Silently</em> add the new virtual override before removing the
	 * specified override, or the <em>update</em> will discover the missing
	 * virtual override and add it preemptively.
	 */
	public V convertOverrideToVirtual(SpecifiedOverride override) {
		if (override.isVirtual()) {
			throw new IllegalArgumentException("Override is already virtual: " + override); //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		S specifiedOverride = (S) override;
		int virtualIndex = this.virtualOverrides.size();
		String overrideName = specifiedOverride.getName();
		V virtualOverride = null;
		// make sure the specified override actually overrides something before building the virtual override
		if (this.overrideWillBeVirtual(overrideName, specifiedOverride)) {
			virtualOverride = this.buildVirtualOverride(overrideName);
			this.virtualOverrides.add(virtualIndex, virtualOverride);
		}

		this.removeSpecifiedOverride(specifiedOverride);  // trigger update

		if (virtualOverride != null) {
			this.fireItemAdded(VIRTUAL_OVERRIDES_LIST, virtualIndex, virtualOverride);
		}
		return virtualOverride;
	}

	/**
	 * Return whether the specified override name will be a
	 * <em>virtual</em> override when the specified specified override is
	 * removed from the container. The override name must be among the
	 * valid override names and it must not correspond to any of the
	 * remaining specified overrides.
	 */
	protected boolean overrideWillBeVirtual(String overrideName, S specifiedOverrideToBeRemoved) {
		return IterableTools.contains(this.getAllOverridableNames(), overrideName) &&
				(this.getSpecifiedOverrideNamed(overrideName, specifiedOverrideToBeRemoved) == null);
	}

	/**
	 * <em>Silently</em> remove the virtual override and add the new specified
	 * override before naming the specified override, or the <em>update</em>
	 * will discover the dangling virtual override and remove it preemptively.
	 */
	public S convertOverrideToSpecified(VirtualOverride override) {
		if ( ! override.isVirtual()) {
			throw new IllegalArgumentException("Override is already specified: " + override); //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		V virtualOverride = (V) override;
		int virtualIndex = this.virtualOverrides.indexOf(virtualOverride);
		this.virtualOverrides.remove(virtualIndex);

		int specifiedIndex = this.specifiedOverrides.size();
		S specifiedOverride = this.buildSpecifiedOverride(this.buildOverrideAnnotation(specifiedIndex));
		this.specifiedOverrides.add(specifiedIndex, specifiedOverride);

		this.initializeSpecifiedOverride(specifiedOverride, virtualOverride);  // trigger update

		this.fireItemRemoved(VIRTUAL_OVERRIDES_LIST, virtualIndex, virtualOverride);
		this.fireItemAdded(SPECIFIED_OVERRIDES_LIST, specifiedIndex, specifiedOverride);		
		return specifiedOverride;
	}

	protected abstract void initializeSpecifiedOverride(S specifiedOverride, V virtualOverride);


	// ********** specified overrides **********

	public ListIterable<S> getSpecifiedOverrides() {
		return IterableTools.cloneLive(this.specifiedOverrides);
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getReadOnlySpecifiedOverrides() {
		// S should always be a subtype of R, but we can't enforce that in the
		// class declaration...
		return (ListIterable<R>) this.getSpecifiedOverrides();
	}

	public int getSpecifiedOverridesSize() {
		return this.specifiedOverrides.size();
	}

	public S getSpecifiedOverride(int index) {
		return this.specifiedOverrides.get(index);
	}

	public S getSpecifiedOverrideNamed(String name) {
		return this.getSpecifiedOverrideNamed(name, null);
	}

	@SuppressWarnings("unchecked")
	protected S getSpecifiedOverrideNamed(String name, S exclude) {
		return (S) this.selectOverrideNamed(this.getReadOnlySpecifiedOverrides(), name, exclude);
	}

	protected S addSpecifiedOverride() {
		return this.addSpecifiedOverride(this.specifiedOverrides.size());
	}

	protected S addSpecifiedOverride(int index) {
		A annotation = this.buildOverrideAnnotation(index);
		return this.addSpecifiedOverride_(index, annotation);
	}

	@SuppressWarnings("unchecked")
	protected A buildOverrideAnnotation(int index) {
		int annotationIndex = this.calculateNewAnnotationIndex(index);
		return (A) this.getResourceMember().addAnnotation(annotationIndex, this.getOverrideAnnotationName());
	}

	protected int calculateNewAnnotationIndex(int index) {
		// if we are adding to the end of the specified list,
		// put the annotation after all the existing annotations
		if (index == this.specifiedOverrides.size()) {
			return IterableTools.size(this.getOverrideAnnotations());
		}

		// if we are adding to the front of the specified list,
		// put the annotation before of all the existing annotations
		if (index == 0) {
			return 0;
		}

		// if we are adding to the middle of the specified list,
		// put the annotation immediately after all the previous override's
		// existing annotation
		return this.translateToAnnotationIndex(index - 1) + 1;
	}

	/**
	 * pre-condition: override exists at the specified index
	 */
	protected int translateToAnnotationIndex(int index) {
		return IterableTools.indexOf(this.getOverrideAnnotations(), this.specifiedOverrides.get(index).getOverrideAnnotation());
	}

	protected abstract String getOverrideAnnotationName();

	protected void removeSpecifiedOverride(S override) {
		this.removeSpecifiedOverride(this.specifiedOverrides.indexOf(override));
	}

	protected void removeSpecifiedOverride(int index) {
		this.removeOverrideAnnotation(index);
		this.removeSpecifiedOverride_(index);
	}

	protected void removeOverrideAnnotation(int index) {
		int annotationIndex = this.translateToAnnotationIndex(index);
		this.getResourceMember().removeAnnotation(annotationIndex, this.getOverrideAnnotationName());
	}

	protected void removeSpecifiedOverride_(int index) {
		this.removeItemFromList(index, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	public void moveSpecifiedOverride(int targetIndex, int sourceIndex) {
		this.moveOverrideAnnotation(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	protected void moveOverrideAnnotation(int targetIndex, int sourceIndex) {
		int targetAnnotationIndex = this.translateToAnnotationIndex(targetIndex);
		int sourceAnnotationIndex = this.translateToAnnotationIndex(sourceIndex);
		this.getResourceMember().moveAnnotation(targetAnnotationIndex, sourceAnnotationIndex, this.getOverrideAnnotationName());
	}

	protected abstract S buildSpecifiedOverride(A overrideAnnotation);

	protected void updateSpecifiedOverrides(IProgressMonitor monitor) {
		ContextContainerTools.update(this.specifiedOverrideContainerAdapter, monitor);
	}

	protected Iterable<A> getRelevantOverrideAnnotations() {
		return IterableTools.filter(this.getOverrideAnnotations(), new AnnotationIsRelevant());
	}

	public class AnnotationIsRelevant
		extends PredicateAdapter<A>
	{
		@Override
		public boolean evaluate(A annotation) {
			String overrideName = annotation.getName();
			return (overrideName != null) && this.getParentAdapter().isRelevant(overrideName);
		}
		protected JavaOverrideContainer2_0.ParentAdapter getParentAdapter() {
			return AbstractJavaOverrideContainer.this.getParentAdapter2_0();
		}
	}

	protected Iterable<A> getOverrideAnnotations() {
		return new SubIterableWrapper<NestableAnnotation, A>((this.overrideAnnotations()));
	}

	protected Iterable<NestableAnnotation> overrideAnnotations() {
		return (this.parentAdapter == null) ? EmptyIterable.<NestableAnnotation>instance() : this.overrideAnnotations_();
	}

	/**
	 * pre-condition: {@link #parentAdapter} is not <code>null</code>
	 */
	protected Iterable<NestableAnnotation> overrideAnnotations_() {
		return this.getResourceMember().getAnnotations(this.getOverrideAnnotationName());
	}

	protected void moveSpecifiedOverride_(int index, S override) {
		this.moveItemInList(index, override, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	protected S addSpecifiedOverride_(int index, A overrideAnnotation) {
		S override = this.buildSpecifiedOverride(overrideAnnotation);
		this.addItemToList(index, override, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
		return override;
	}

	protected void removeSpecifiedOverride_(S override) {
		this.removeSpecifiedOverride_(this.specifiedOverrides.indexOf(override));
	}

	/**
	 * specified override container adapter
	 */
	protected class SpecifiedOverrideContainerAdapter
		implements ContextContainerTools.Adapter<S, A>
	{
		public Iterable<S> getContextElements() {
			return AbstractJavaOverrideContainer.this.getSpecifiedOverrides();
		}
		public Iterable<A> getResourceElements() {
			return AbstractJavaOverrideContainer.this.getRelevantOverrideAnnotations();
		}
		@SuppressWarnings("unchecked")
		public A getResourceElement(S contextElement) {
			return (A) contextElement.getOverrideAnnotation();
		}
		public void moveContextElement(int index, S element) {
			AbstractJavaOverrideContainer.this.moveSpecifiedOverride_(index, element);
		}
		public void addContextElement(int index, A resourceElement) {
			AbstractJavaOverrideContainer.this.addSpecifiedOverride_(index, resourceElement);
		}
		public void removeContextElement(S element) {
			AbstractJavaOverrideContainer.this.removeSpecifiedOverride_(element);
		}
	}


	// ********** virtual overrides **********

	public ListIterable<V> getVirtualOverrides() {
		return IterableTools.cloneLive(this.virtualOverrides);
	}

	public int getVirtualOverridesSize() {
		return this.virtualOverrides.size();
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getReadOnlyVirtualOverrides() {
		// V should always be a subtype of R, but we can't enforce that in the
		// class declaration...
		return (ListIterable<R>) this.getVirtualOverrides();
	}

	protected void updateVirtualOverrides(IProgressMonitor monitor) {
		ContextContainerTools.update(this.virtualOverrideContainerAdapter, monitor);
	}

	/**
	 * Return the overridable names that are not already in the list of
	 * specified overrides.
	 */
	protected Iterable<String> getVirtualOverrideNames() {
		return IterableTools.filter(this.getAllOverridableNames(), new OverrideIsVirtual());
	}

	public class OverrideIsVirtual
		extends PredicateAdapter<String>
	{
		@Override
		public boolean evaluate(String overrideName) {
			return AbstractJavaOverrideContainer.this.overrideIsVirtual(overrideName);
		}
	}

	protected boolean overrideIsVirtual(String name) {
		return this.getSpecifiedOverrideNamed(name) == null;
	}

	protected void moveVirtualOverride(int index, V override) {
		this.moveItemInList(index, override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
	}

	protected V addVirtualOverride(int index, String name) {
		V override = this.buildVirtualOverride(name);
		this.addItemToList(index, override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
		return override;
	}

	protected abstract V buildVirtualOverride(String name);

	protected void removeVirtualOverride(V override) {
		this.removeItemFromList(override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
	}

	/**
	 * virtual override container adapter
	 * NB: the context override is matched with a resource override by name
	 */
	protected class VirtualOverrideContainerAdapter
		implements ContextContainerTools.Adapter<V, String>
	{
		public Iterable<V> getContextElements() {
			return AbstractJavaOverrideContainer.this.getVirtualOverrides();
		}
		public Iterable<String> getResourceElements() {
			return AbstractJavaOverrideContainer.this.getVirtualOverrideNames();
		}
		public String getResourceElement(V contextElement) {
			return contextElement.getName();
		}
		public void moveContextElement(int index, V element) {
			AbstractJavaOverrideContainer.this.moveVirtualOverride(index, element);
		}
		public void addContextElement(int index, String resourceElement) {
			AbstractJavaOverrideContainer.this.addVirtualOverride(index, resourceElement);
		}
		public void removeContextElement(V element) {
			AbstractJavaOverrideContainer.this.removeVirtualOverride(element);
		}
	}


	// ********** misc **********

	protected JavaOverrideContainer2_0.ParentAdapter getParentAdapter2_0() {
		return (JavaOverrideContainer2_0.ParentAdapter) this.parentAdapter;
	}

	public TypeMapping getTypeMapping() {
		return this.parentAdapter.getTypeMapping();
	}

	protected JavaResourceMember getResourceMember() {
		return this.parentAdapter.getResourceMember();
	}

	public TypeMapping getOverridableTypeMapping() {
		return this.parentAdapter.getOverridableTypeMapping();
	}

	public Iterable<String> getAllOverridableNames() {
		return (this.parentAdapter != null) ? this.parentAdapter.getAllOverridableNames() : EmptyIterable.<String>instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.parentAdapter.tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.parentAdapter.getCandidateTableNames();
	}

	public Table resolveDbTable(String tableName) {
		return this.parentAdapter.resolveDbTable(tableName);
	}

	public String getDefaultTableName() {
		return this.parentAdapter.getDefaultTableName();
	}

	public JpaValidator buildOverrideValidator(Override_ override) {
		return this.parentAdapter.buildOverrideValidator(override, this);
	}

	public JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter tableParentAdapter) {
		return this.parentAdapter.buildColumnValidator(override, column, tableParentAdapter);
	}

	public String getPossiblePrefix() {
		return this.getParentAdapter2_0().getPossiblePrefix();
	}

	public String getWritePrefix() {
		return this.getParentAdapter2_0().getWritePrefix();
	}

	protected R selectOverrideNamed(Iterable<R> overrides, String name) {
		return this.selectOverrideNamed(overrides, name, null);
	}

	protected R selectOverrideNamed(Iterable<R> overrides, String name, S exclude) {
		for (R override : overrides) {
			if (override == exclude) {
				continue;  // skip
			}
			if (ObjectTools.equals(override.getName(), name)) {
				return override;
			}
		}
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		StringBuilderTools.append(sb, this.getOverrides());
	}


	// ********** code completion **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (R override : this.getOverrides()) {
			result = override.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (R override: this.getOverrides()) {
			override.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getValidationAnnotationTextRange();
		return (textRange != null) ? textRange : this.parentAdapter.getValidationTextRange();
	}

	protected TextRange getValidationAnnotationTextRange() {
		Annotation annotation = this.getValidationAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}

	protected Annotation getValidationAnnotation() {
		JavaResourceMember resourceMember = this.getResourceMember();
		return resourceMember.getAnnotation(this.getOverrideAnnotationName());
	}
}
