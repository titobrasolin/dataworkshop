package dataWorkshop.data.transformer;

import org.w3c.dom.Element;

import dataWorkshop.data.Data;
import dataWorkshop.data.DataTransformer;
import dataWorkshop.xml.XMLSerializeFactory;

/**
 * value ist treated as a series of 32 bits, the most significant bits are used as the pattern to fill
 * <p>
 * DataWorkshop - a binary data editor 
 * <br>
 * Copyright (C) 2000, 2004  Martin Pape (martin.pape@gmx.de)
 * <br>
 * <br>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <br>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <br>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * </p>
 */
public class Fill extends DataTransformer {
    
    final static String CLASS_NAME = "Fill";
    Data pattern;
       
    public Fill() {
        super();
    }
    
    public Fill(Data pattern) {
        super((int) pattern.getBitSize());
        this.pattern = pattern;
    }
    
    /******************************************************************************
     *	XMLSerializeable Interface
     */
     public String getClassName() {
        return CLASS_NAME;
    }
    
    public void serialize(Element context) {
        super.serialize(context);
        XMLSerializeFactory.serialize(context, getPattern());
    }
    
    public void deserialize(Element context) {
        super.deserialize(context);
        setPattern((Data) XMLSerializeFactory.deserializeFirst(context));
    }
    
    /******************************************************************************
     *	Public Methods
     */
    public Data getPattern() {
        return pattern;
    }
    
    public void setPattern(Data pattern) {
        this.pattern = pattern;
        setBitSize((int) pattern.getBitSize());
    }
    
    public Data transform(Data data, long bitOffset, long bitSize) {
        Data result = data.copy(bitOffset, bitSize);
        for (long i = 0; i < result.getBitSize(); i += getBitSize()) {
            result.replace(i, getBitSize(), pattern);
        }
        return result;
    }
}