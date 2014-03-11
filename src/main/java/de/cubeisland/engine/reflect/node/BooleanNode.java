/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Anselm Brehme, Phillip Schichtel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.cubeisland.engine.reflect.node;

/**
 * A Boolean Node
 */
public class BooleanNode extends Node<Boolean>
{
    private final boolean bool;

    /**
     * Creates a BooleanNode
     *
     * @param bool a boolean
     */
    public BooleanNode(boolean bool)
    {
        this.bool = bool;
    }

    @Override
    public Boolean getValue()
    {
        return this.bool;
    }

    @Override
    public String asText()
    {
        return String.valueOf(bool);
    }

    /**
     * Creates a "False" Node
     *
     * @return the BooleanNode
     */
    public static BooleanNode falseNode()
    {
        return new BooleanNode(false);
    }

    /**
     * Creates a "True" Node
     *
     * @return the BooleanNode
     */
    public static BooleanNode trueNode()
    {
        return new BooleanNode(true);
    }

    /**
     * Creates a BooleanNode
     *
     * @param bool a boolean
     *
     * @return the BooleanNode
     */
    public static BooleanNode of(boolean bool)
    {
        return bool ? trueNode() : falseNode();
    }

    @Override
    public String toString()
    {
        return "BooleanNode=[" + bool + "]";
    }
}