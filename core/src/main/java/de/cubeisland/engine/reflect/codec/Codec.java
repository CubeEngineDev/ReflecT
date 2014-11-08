/**
 * The MIT License
 * Copyright (c) 2013 Cube Island
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.cubeisland.engine.reflect.codec;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import de.cubeisland.engine.reflect.Reflected;
import de.cubeisland.engine.reflect.Section;
import de.cubeisland.engine.reflect.codec.converter.SectionConverter;
import de.cubeisland.engine.reflect.util.SectionFactory;
import de.cubeisland.engine.reflect.annotations.Comment;
import de.cubeisland.engine.reflect.codec.converter.Converter;
import de.cubeisland.engine.reflect.codec.converter.generic.CollectionConverter;
import de.cubeisland.engine.reflect.codec.converter.generic.MapConverter;
import de.cubeisland.engine.reflect.exception.ConversionException;
import de.cubeisland.engine.reflect.exception.FieldAccessException;
import de.cubeisland.engine.reflect.exception.InvalidReflectedObjectException;
import de.cubeisland.engine.reflect.exception.UnsupportedReflectedException;
import de.cubeisland.engine.reflect.node.ErrorNode;
import de.cubeisland.engine.reflect.node.KeyNode;
import de.cubeisland.engine.reflect.node.ListNode;
import de.cubeisland.engine.reflect.node.MapNode;
import de.cubeisland.engine.reflect.node.Node;
import de.cubeisland.engine.reflect.node.NullNode;
import de.cubeisland.engine.reflect.node.StringNode;

import static de.cubeisland.engine.reflect.codec.FieldType.*;

/**
 * This abstract Codec can be implemented to read and write reflected objects that allow child-reflected
 */
public abstract class Codec<Input, Output>
{
    private ConverterManager converterManager;

    /**
     * Called via registering with the CodecManager
     */
    final void init(ConverterManager converterManager)
    {
        this.converterManager = converterManager;
    }

    /**
     * Returns the {@link ConverterManager} for this codec, allowing to register custom {@link Converter} for this codec only
     *
     * @return the ConverterManager
     *
     * @throws UnsupportedOperationException if the Codec was not instantiated by the Factory
     */
    public final ConverterManager getConverterManager()
    {
        if (converterManager == null)
        {
            throw new UnsupportedOperationException(
                "This codec is not registered in the CodecManager and therefor has no ConverterManager for its own converters");
        }
        return converterManager;
    }

    /**
     * Loads in the given {@link Reflected} using the <code>Input</code>
     *
     * @param reflected the Reflected to load
     * @param input     the Input to load from
     */
    public abstract void loadReflected(Reflected reflected, Input input);

    /**
     * Saves the {@link Reflected} using given <code>Output</code>
     *
     * @param reflected the Reflected to save
     * @param output    the Output to save into
     */
    public abstract void saveReflected(Reflected reflected, Output output);

    /**
     * Saves the values contained in the {@link MapNode} using given <code>Output</code>
     *
     * @param node      the MapNode containing all data to save
     * @param out       the Output to save to
     * @param reflected the Reflected
     */
    protected abstract void save(MapNode node, Output out, Reflected reflected) throws ConversionException;

    /**
     * Converts the <code>Input</code> into a {@link MapNode}
     *
     * @param in        the Input to load from
     * @param reflected the Reflected
     */
    protected abstract MapNode load(Input in, Reflected reflected) throws ConversionException;


    /**
     * Converts given Reflected into a MapNode
     *
     * @param reflected the Reflected to convert
     *
     * @return the MapNode
     */
    public final MapNode convertReflected(Reflected reflected)
    {
        try
        {
            return (MapNode)reflected.getConverterManager().withFallback(this.converterManager).convertToNode(
                reflected);
        }
        catch (ConversionException e)
        {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Converts a MapNode to fill a Reflected with values
     *
     * @param reflected the Reflected to fill
     * @param node      the MapNode
     */
    public final void fillReflected(Reflected reflected, MapNode node)
    {
        try
        {
            reflected.getConverterManager().withFallback(this.converterManager).convertFromNode(node, reflected);
        }
        catch (ConversionException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
