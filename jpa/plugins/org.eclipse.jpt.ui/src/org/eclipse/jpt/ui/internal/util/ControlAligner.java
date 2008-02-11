/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * This class is responsible to set a preferred width on the registered widgets
 * (either <code>Control</code> or <code>ControlAligner</code>) based on the
 * widest widget.
 * <p>
 * Important: The layout data has to be a <code>GridData</code>. If none is set,
 * then a new <code>GridData</code> is automatically created.
 * <p>
 * Here an example of the result if this aligner is used to align controls
 * within either one or two group boxes, the controls added are the labels in
 * this case. It is also possible to align controls on the right side of the
 * main component, a spacer can be used for extra space.
 * <p>
 * Here's an example:
 * <pre>
 * - Group Box 1 ---------------------------------------------------------------
 * |                     -------------------------------------- -------------- |
 * | Name:               | I                                  | | Browsse... | |
 * |                     -------------------------------------- -------------- |
 * |                     ---------                                             |
 * | Preallocation Size: |     |I|                                             |
 * |                     ---------                                             |
 * |                     --------------------------------------                |
 * | Descriptor:         |                                  |v|                |
 * |                     --------------------------------------                |
 * -----------------------------------------------------------------------------
 *
 * - Group Box 2 ---------------------------------------------------------------
 * |                     --------------------------------------                |
 * | Mapping Type:       |                                  |V|                |
 * |                     --------------------------------------                |
 * |                     --------------------------------------                |
 * | Check in Script:    | I                                  |                |
 * |                     --------------------------------------                |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class ControlAligner
{
	/**
	 * Flag used to prevent a validation so it can be done after an operation
	 * completed.
	 */
	private boolean autoValidate;

	/**
	 * The utility class used to support bound properties.
	 */
	private Collection<Listener> changeSupport;

	/**
	 * The listener added to each of the controls that listens only to a text
	 * change.
	 */
	private Listener listener;

	/**
	 * Prevents infinite recursion when recalculating the preferred width.
	 * This happens in an hierarchy of <code>ControlAligner</code>s. The lock
	 * has to be placed here and not in the {@link ControlAlignerWrapper}.
	 */
	private boolean locked;

	/**
	 * The length of the widest control. If the length was not calculated, then
	 * this value is -1.
	 */
	private int maximumWidth;

	/**
	 * The collection of {@link Wrapper}s encapsulating either <code>Control</code>s
	 * or {@link ControlAligner}s.
	 */
	private Collection<Wrapper> wrappers;

	/**
	 * A null-<code>Point</code> object used to clear the preferred size.
	 */
	private static final Point DEFAULT_SIZE = new Point(SWT.DEFAULT, SWT.DEFAULT);

	/**
	 * The types of events to listen in order to properly adjust the size of all
	 * the widgets.
	 */
	private static final int[] EVENT_TYPES = {
		SWT.Dispose,
		SWT.Show,
		SWT.Resize,
		SWT.Hide
	};

	/**
	 * Creates a new <code>ControlAligner</code>.
	 */
	public ControlAligner() {
		super();
		initialize();
	}

	/**
	 * Creates a new <code>ControlAligner</code>.
	 *
	 * @param controls The collection of <code>Control</code>s
	 */
	public ControlAligner(Collection<? extends Control> controls) {
		this();
		addAllComponents(controls);
	}

	/**
	 * Adds the given control. Its width will be used along with the width of all
	 * the other registered controls in order to get the greater witdh and use
	 * it as the width for all the controls.
	 *
	 * @param control The <code>Control</code> to be added
	 */
	public void add(Control control) {

		Wrapper wrapper = buildWrapper(control);
		wrapper.addListener(listener);
		wrappers.add(wrapper);

		revalidate();
	}

	/**
	 * Adds the given control. Its width will be used along with the width of all
	 * the other registered controls in order to get the greater witdh and use
	 * it as the width for all the controls.
	 *
	 * @param controlAligner The <code>ControlAligner</code> to be added
	 * @exception IllegalArgumentException Can't add the <code>ControlAligner</code>
	 * to itself
	 */
	public void add(ControlAligner controlAligner) {

		Assert.isLegal(controlAligner != this, "Can't add the ControlAligner to itself");

		Wrapper wrapper = buildWrapper(controlAligner);
		wrapper.addListener(listener);
		wrappers.add(wrapper);

		if (!controlAligner.wrappers.isEmpty()) {
			revalidate();
		}
	}

	/**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest control and use its width as the width for all the controls.
	 *
	 * @param items The collection of <code>Control</code>s
	 */
	public void addAll(Collection<? extends Control> items) {

		// Deactivate the auto validation while adding all the Controls
		// in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (Control item : items) {
			add(item);
		}

		autoValidate = oldAutoValidate;
		revalidate();
	}

	/**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest component and use its width as the width for all the components.
	 *
	 * @param items The collection of <code>ControlAligner</code>s
	 */
	public void addAllComponentAligners(Collection<ControlAligner> aligners) {

		// Deactivate the auto validation while adding all the JComponents and/or
		// ComponentAligners in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (ControlAligner aligner : aligners) {
			add(aligner);
		}

		autoValidate = oldAutoValidate;
		revalidate();
	}

   /**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest component and use its width as the width for all the components.
	 *
	 * @param items The collection of <code>Control</code>s
	 */
	public void addAllComponents(Collection<? extends Control> components) {

		// Deactivate the auto validation while adding all the JComponents and/or
		// ComponentAligners in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (Control component : components) {
			add(component);
		}

		autoValidate = oldAutoValidate;
		revalidate();
	}

   /**
	 * Adds the given <code>ControListener</code>.
	 *
	 * @param listener The <code>Listener</code> to be added
	 */
	private void addListener(Listener listener) {

		if (changeSupport == null) {
		    changeSupport = new ArrayList<Listener>();
		}

		changeSupport.add(listener);
   }

	/**
	 * Creates a new <code>Wrapper</code> that encapsulates the given source.
	 *
	 * @param control The control to be wrapped
	 * @return A new {@link Wrapper}
	 */
	private Wrapper buildWrapper(Control control) {
		return new ControlWrapper(control);
	}

   /**
	 * Creates a new <code>Wrapper</code> that encapsulates the given source.
	 *
	 * @param ControlAligner The <code>ControlAligner</code> to be wrapped
	 * @return A new {@link ControlAlignerWrapper}
	 */
	private Wrapper buildWrapper(ControlAligner ControlAligner) {
		return new ControlAlignerWrapper(ControlAligner);
	}

	/**
	 * Reports a bound property change.
	 *
	 * @param oldValue the old value of the property (as an int)
	 * @param newValue the new value of the property (as an int)
	 */
	private void controlResized(int oldValue, int newValue) {

		if ((changeSupport != null) && (oldValue != newValue)) {
			Event event  = new Event();
			event.widget = SWTUtil.getShell();
			event.data   = this;

			for (Listener listener : changeSupport) {
				listener.handleEvent(event);
			}
		}
	}

	/**
	 * Returns the length of the widest control. If the length was not
	 * calculated, then this value is -1.
	 *
	 * @return The width of the widest control or -1 if the length has not
	 * been calculated yet
	 */
	public int getMaximumWidth() {
		return maximumWidth;
	}

	/**
	 * Initializes this <code>ControlAligner</code>.
	 */
	private void initialize() {

		this.autoValidate = true;
		this.maximumWidth = -1;
		this.listener     = new ListenerHandler();
		this.wrappers     = new ArrayList<Wrapper>();
	}

	/**
	 * Invalidates the size of the given object.
	 *
	 * @param source The source object to be invalidated
	 */
	private void invalidate(Object source) {

		Wrapper wrapper = retrieveWrapper(source);

		if (!wrapper.isLocked()) {
			Point size = wrapper.cachedSize();
			size.x = size.y = 0;
			wrapper.setSize(DEFAULT_SIZE);
		}
	}

	/**
	 * Updates the maximum length based on the widest control. This methods
	 * does not update the width of the controls.
	 */
	private void recalculateWidth() {

		int width = -1;

		for (Wrapper wrapper : wrappers) {
			Point size = wrapper.cachedSize();

			// The size has not been calculated yet
			if (size.y == 0) {
				size = wrapper.size();
			}

			// Only keep the greatest width
			width = Math.max(size.x, width);
		}

		locked = true;

		try {
			setMaximumWidth(width);
		}
		finally {
			locked = false;
		}
	}

	/**
	 * Removes the given control. Its preferred width will not be used when
	 * calculating the preferred width.
	 *
	 * @param control The control to be removed
	 * @exception AssertionFailedException If the given <code>Control</code> is
	 * <code>null</code>
	 */
	public void remove(Control control) {

		Assert.isNotNull(control, "The Control to remove cannot be null");

		Wrapper wrapper = retrieveWrapper(control);
		wrapper.removeListener(listener);
		wrappers.remove(wrapper);

		revalidate();
	}

	/**
	 * Removes the given <code>ControlAligner</code>. Its preferred width
	 * will not be used when calculating the preferred witdh.
	 *
	 * @param controlAligner The <code>ControlAligner</code> to be removed
	 * @exception AssertionFailedException If the given <code>ControlAligner</code>
	 * is <code>null</code>
	 */
	public void remove(ControlAligner controlAligner) {

		Assert.isNotNull(controlAligner, "The ControlAligner to remove cannot be null");

		Wrapper wrapper = retrieveWrapper(controlAligner);
		wrapper.removeListener(listener);
		wrappers.remove(wrapper);

		revalidate();
	}

	/**
	 * Removes the given <code>Listener</code>.
	 *
	 * @param listener The <code>Listener</code> to be removed
	 */
	private void removeListener(Listener listener) {

		changeSupport.remove(listener);

		if (changeSupport.isEmpty()) {
			changeSupport = null;
		}
	}

	/**
	 * Retrieves the <code>Wrapper</code> that is encapsulating the given object.
	 *
	 * @param source Either a <code>Control</code> or a <code>ControlAligner</code>
	 * @return Its <code>Wrapper</code>
	 */
	private Wrapper retrieveWrapper(Object source) {

		for (Wrapper wrapper : wrappers) {
			if (wrapper.source() == source) {
				return wrapper;
			}
		}

		throw new IllegalArgumentException("Can't retrieve the Wrapper for " + source);
	}

	/**
	 * If the count of control is greater than one and {@link #isAutoValidate()}
	 * returns <code>true</code>, then the size of all the registered
	 * <code>Control</code>s will be udpated.
	 */
	private void revalidate() {

		if (autoValidate) {
			recalculateWidth();
			revalidateImp();
		}
	}

	/**
	 * Bases on the information contained in the given <code>Event</code>,
	 * resize the controls.
	 *
	 * @param event The <code>Event</code> sent by the UI thread when the state
	 * of a widget changed
	 */
	private void revalidate(Event event) {

		// We don't need to revalidate during a revalidation process
		if (locked) {
			return;
		}

		Object source;

		if (event.widget != SWTUtil.getShell()) {
			source = event.widget;
			Control control = (Control) source;

			// When a dialog is opened, we need to actually force a layout of
			// the controls, this is required because the control is actually
			// not visible when the preferred width is caculated
			if (control == control.getShell()) {
				if (event.type == SWT.Dispose) {
					return;
				}

				source = null;
			}
		}
		else {
			source = event.data;
		}

		// Either remove the ControlWrapper if the widget was disposed or
		// invalidate the widget in order to recalculate the preferred size
		if (source != null) {
			if (event.type == SWT.Dispose) {
				Wrapper wrapper = retrieveWrapper(source);
				wrappers.remove(wrapper);
			}
			else {
				invalidate(source);
			}
		}

		// Now revalidate all the Controls and ControlAligners
		revalidate();
	}

	/**
	 * Updates the size of every control based on the maximum width.
	 */
	private void revalidateImp() {

		for (Wrapper wrapper : wrappers) {
			Point size = wrapper.cachedSize();
			size = new Point(maximumWidth, size.y);
			wrapper.setSize(size);
		}
	}

	/**
	 * Sets the length of the widest control. If the length was not calulcated,
	 * then this value is -1.
	 *
	 * @param maximumWidth The width of the widest control
	 */
	private void setMaximumWidth(int maximumWidth) {

		int oldMaximumWidth = getMaximumWidth();
		this.maximumWidth = maximumWidth;
		controlResized(oldMaximumWidth, maximumWidth);
	}

	/**
	 * Returns the size by determining which control has the greatest
	 * width.
	 *
	 * @return The size of this <code>ControlAligner</code>, which is
	 * {@link #getMaximumWidth()} for the width
	 */
	private Point size() {

		if (maximumWidth == -1) {
			recalculateWidth();
		}

		return new Point(maximumWidth, 0);
	}

	/**
	 * Returns a string representation of this <code>ControlAligner</code>.
	 *
	 * @return Information about this object
	 */
	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("maximumWidth=");
		sb.append(maximumWidth);
		sb.append(", wrappers=");
		sb.append(wrappers);
		return StringTools.buildToStringFor(this, sb);
	}

	/**
	 * This <code>Wrapper</code> encapsulates a {@link ControlAligner}.
	 */
	private class ControlAlignerWrapper implements Wrapper
	{
		/**
		 * The cached size, which is {@link ControlAligner#maximumWidth}.
		 */
		private Point cachedSize;

		/**
		 * The <code>ControlAligner</code> encapsulated by this
		 * <code>Wrapper</code>.
		 */
		private final ControlAligner controlAligner;

		/**
		 * Creates a new <code>ControlAlignerWrapper</code> that encapsulates
		 * the given <code>ControlAligner</code>.
		 *
		 * @param controlAligner The <code>ControlAligner</code> to be
		 * encapsulated by this <code>Wrapper</code>
		 */
		private ControlAlignerWrapper(ControlAligner controlAligner) {
			super();

			this.controlAligner = controlAligner;
			cachedSize = new Point(controlAligner.maximumWidth, 0);
		}

		/*
		 * (non-Javadoc)
		 */
		public void addListener(Listener listener) {
			controlAligner.addListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public Point cachedSize() {
			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isLocked() {
			return controlAligner.locked;
		}

		/*
		 * (non-Javadoc)
		 */
		public void removeListener(Listener listener) {
			controlAligner.removeListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public void setSize(Point size) {
			if (size == DEFAULT_SIZE)
			{
				controlAligner.maximumWidth = -1;
			}
			else if (controlAligner.maximumWidth != size.x)
			{
				controlAligner.maximumWidth = size.x;
				controlAligner.revalidateImp();
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public Point size() {
			return controlAligner.size();
		}

		/*
		 * (non-Javadoc)
		 */
		public Object source() {
			return controlAligner;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String toString() {

			StringBuffer sb = new StringBuffer();
			sb.append("Cached size=");
			sb.append(cachedSize);
			sb.append(", ControlAligner=");
			sb.append(controlAligner);
			return StringTools.buildToStringFor(this, sb);
		}
	}

	/**
	 * This <code>Wrapper</code> encapsulates a {@link Control}.
	 */
	private class ControlWrapper implements Wrapper
	{
		/**
		 * The cached size, which is control's size.
		 */
		private final Point cachedSize;

		/**
		 * The control to be encapsulated by this <code>Wrapper</code>.
		 */
		private final Control control;

		/**
		 * Creates a new <code>controlWrapper</code> that encapsulates the given
		 * control.
		 *
		 * @param control The control to be encapsulated by this <code>Wrapper</code>
		 */
		private ControlWrapper(Control control) {
			super();

			this.control    = control;
			this.cachedSize = new Point(0, 0);
		}

		/*
		 * (non-Javadoc)
		 */
		public void addListener(Listener listener) {

			for (int eventType : EVENT_TYPES) {
				control.addListener(eventType, listener);
			}

//			for (int eventType : SHELL_EVENT_TYPES) {
//				control.getShell().addListener(eventType, listener);
//			}
		}

		/*
		 * (non-Javadoc)
		 */
		public Point cachedSize() {
			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isLocked() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean isVisible() {
			return control.isVisible();
		}

		/*
		 * (non-Javadoc)
		 */
		public void removeListener(Listener listener) {

			for (int eventType : EVENT_TYPES) {
				control.removeListener(eventType, listener);
			}

//			for (int eventType : SHELL_EVENT_TYPES) {
//				control.getShell().removeListener(eventType, listener);
//			}
		}

		/*
		 * (non-Javadoc)
		 */
		public void setSize(Point size) {

			if (control.isDisposed()) {
				return;
			}

			// Update the GridData with the new size
			GridData data = (GridData) control.getLayoutData();

			if (data == null) {
				data = new GridData();
				data.horizontalAlignment = SWT.FILL;
				control.setLayoutData(data);
			}

			data.widthHint  = size.x;
			data.heightHint = size.y;

			// Force the control to be resized, and tell its parent to layout
			// its widgets
			if (size.x > 0) {
				Rectangle bounds = control.getBounds();

				// Only update the control's width if it's
				// different from the current size
				if (bounds.width != size.x) {
					locked = true;

					try {
						control.setBounds(bounds.x, bounds.y, size.x, bounds.height);
						control.getParent().layout(true);
					}
					finally
					{
						locked = false;
					}
				}
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public Point size() {
			Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			cachedSize.x = size.x;
			cachedSize.y = size.y;
			return size;
		}

		/*
		 * (non-Javadoc)
		 */
		public Object source() {
			return control;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String toString() {

			StringBuffer sb = new StringBuffer();
			sb.append("Cached size=");
			sb.append(cachedSize);
			sb.append(", Control=");
			sb.append(control);
			return StringTools.buildToStringFor(this, sb);
		}
	}

	/**
	 * The listener added to each of the control that is notified in order to
	 * revalidate the preferred size.
	 */
	private class ListenerHandler implements Listener
	{
		public void handleEvent(Event event) {
			ControlAligner.this.revalidate(event);
		}
	}

	/**
	 * This <code>Wrapper</code> helps to encapsulate heterogeneous objects and
	 * apply the same behavior on them.
	 */
	private interface Wrapper
	{
	   /**
		 * Adds the given <code>Listener</code> to wrapped object in order to
		 * receive notification when its property changed.
		 *
		 * @param listener The <code>Listener</code> to be added
		 */
		void addListener(Listener listener);

		/**
		 * Returns the cached size of the encapsulated source.
		 *
		 * @return A non-<code>null</code> <code>Point</code> where the x is the
		 * width and the y is the height of the widget
		 */
		Point cachedSize();

		/**
		 * Prevents infinite recursion when recalculating the preferred width.
		 * This happens in an hierarchy of <code>ControlAligner</code>s.
		 *
		 * @return <code>true</code> to prevent this <code>Wrapper</code> from
		 * being invalidated; otherwise <code>false</code>
		 */
		boolean isLocked();

		/**
		 * Removes the given <code>Listener</code>.
		 *
		 * @param listener The <code>Listener</code> to be removed
		 */
		void removeListener(Listener listener);

		/**
		 * Sets the size on the encapsulated source.
		 *
		 * @param size The new size
		 */
		void setSize(Point size);

		/**
		 * Returns the preferred size of the wrapped source.
		 *
		 * @return The preferred size
		 */
		Point size();

		/**
		 * Returns the encapsulated object.
		 *
		 * @return The object that is been wrapped
		 */
		Object source();
	}
}