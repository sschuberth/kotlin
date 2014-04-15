package templates

import templates.Family.*

fun elements(): List<GenericFunction> {
    val templates = arrayListOf<GenericFunction>()

    templates add f("contains(element: T)") {
        doc { "Returns true if *element* is found in the collection" }
        returns("Boolean")
        body {
            """
            if (this is Collection<*>)
                return contains(element)
            return indexOf(element) >= 0
            """
        }
        exclude(Strings, Lists, Collections)
        body(ArraysOfPrimitives, ArraysOfObjects) {
            """
            return indexOf(element) >= 0
            """
        }
    }

    templates add f("indexOf(element: T)") {
        exclude(Strings)
        doc { "Returns first index of *element*, or -1 if the collection does not contain element" }
        returns("Int")
        body {
            """
            var index = 0
            for (item in this) {
                if (element == item)
                    return index
                index++
            }
            return -1
            """
        }

        body(ArraysOfObjects) {
            """
            if (element == null) {
                for (index in indices) {
                    if (this[index] == null) {
                        return index
                    }
                }
            } else {
                for (index in indices) {
                    if (element == this[index]) {
                        return index
                    }
                }
            }
            return -1
           """
        }
        body(ArraysOfPrimitives) {
            """
            for (index in indices) {
                if (element == this[index]) {
                    return index
                }
            }
            return -1
           """
        }
    }

    templates add f("lastIndexOf(element: T)") {
        exclude(Strings) // has native implementation
        doc { "Returns last index of *element*, or -1 if the collection does not contain element" }
        returns("Int")
        body {
            """
            var lastIndex = -1
            var index = 0
            for (item in this) {
                if (element == item)
                    lastIndex = index
                index++
            }
            return lastIndex
            """
        }

        body(Lists, ArraysOfObjects) {
            """
            if (element == null) {
                for (index in indices.reverse()) {
                    if (this[index] == null) {
                        return index
                    }
                }
            } else {
                for (index in indices.reverse()) {
                    if (element == this[index]) {
                        return index
                    }
                }
            }
            return -1
           """
        }
        body(ArraysOfPrimitives) {
            """
            for (index in indices.reverse()) {
                if (element == this[index]) {
                    return index
                }
            }
            return -1
           """
        }
    }

    templates add f("elementAt(index: Int)") {
        doc { "Returns element at given *index*" }
        returns("T")
        body {
            """
            if (this is List<*>)
                return get(index) as T
            val iterator = iterator()
            var count = 0
            while (iterator.hasNext()) {
                val element = iterator.next()
                if (index == count++)
                    return element
            }
            throw IndexOutOfBoundsException("Collection doesn't contain element at index")
            """
        }
        body(Streams) {
            """
            val iterator = iterator()
            var count = 0
            while (iterator.hasNext()) {
                val element = iterator.next()
                if (index == count++)
                    return element
            }
            throw IndexOutOfBoundsException("Collection doesn't contain element at index")
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            return get(index)
            """
        }
    }

    templates add f("first()") {
        doc { "Returns first element" }
        returns("T")
        body {
            """
            when (this) {
                is List<*> -> {
                    if (size == 0)
                        throw IllegalArgumentException("Collection is empty")
                    else
                        return this[0] as T
                }
                else -> {
                    val iterator = iterator()
                    if (!iterator.hasNext())
                        throw IllegalArgumentException("Collection is empty")
                    return iterator.next()
                }
            }
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (size == 0)
                throw IllegalArgumentException("Collection is empty")
            return this[0]
            """
        }
    }
    templates add f("firstOrNull()") {
        doc { "Returns first element, or null if collection is empty" }
        returns("T?")
        body {
            """
            when (this) {
                is List<*> -> {
                    if (size == 0)
                        return null
                    else
                        return this[0] as T
                }
                else -> {
                    val iterator = iterator()
                    if (!iterator.hasNext())
                        return null
                    return iterator.next()
                }
            }
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            return if (size > 0) this[0] else null
            """
        }
    }

    templates add f("first(predicate: (T) -> Boolean)") {
        inline(true)

        doc { "Returns first element matching the given *predicate*" }
        returns("T")
        body {
            """
            for (element in this) if (predicate(element)) return element
            throw IllegalArgumentException("No element matching predicate was found")
            """
        }
    }

    templates add f("firstOrNull(predicate: (T) -> Boolean)") {
        inline(true)

        doc { "Returns first element matching the given *predicate*, or *null* if element was not found" }
        returns("T?")
        body {
            """
            for (element in this) if (predicate(element)) return element
            return null
            """
        }
    }

    templates add f("last()") {
        doc { "Returns last element" }
        returns("T")
        body {
            """
            when (this) {
                is List<*> -> {
                    if (size == 0)
                        throw IllegalArgumentException("Collection is empty")
                    else
                        return this[size - 1] as T
                }
                else -> {
                    val iterator = iterator()
                    if (!iterator.hasNext())
                        throw IllegalArgumentException("Collection is empty")
                    var last = iterator.next()
                    while (iterator.hasNext())
                        last = iterator.next()
                    return last
                }
            }
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (size == 0)
                throw IllegalArgumentException("Collection is empty")
            return this[size - 1]
            """
        }
    }

    templates add f("lastOrNull()") {
        doc { "Returns last element, or null if collection is empty" }
        returns("T?")
        body {
            """
            when (this) {
                is List<*> -> return if (size > 0) this[size - 1] as T else null
                else -> {
                    val iterator = iterator()
                    if (!iterator.hasNext())
                        return null
                    var last = iterator.next()
                    while (iterator.hasNext())
                        last = iterator.next()
                    return last
                }
            }
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            return if (size > 0) this[size - 1] else null
            """
        }
    }

    templates add f("last(predicate: (T) -> Boolean)") {
        inline(true)
        doc { "Returns last element matching the given *predicate*" }
        returns("T")
        body {
            """
            var last: T? = null
            var found = false
            for (element in this) {
                if (predicate(element)) {
                    last = element
                    found = true
                }
            }
            if (!found) throw IllegalArgumentException("Collection doesn't contain any element matching predicate")
            return last as T
            """
        }
    }

    templates add f("lastOrNull(predicate: (T) -> Boolean)") {
        inline(true)
        doc { "Returns last element matching the given *predicate*, or null if element was not found" }
        returns("T?")
        body {
            """
            var last: T? = null
            for (element in this) {
                if (predicate(element)) {
                    last = element
                }
            }
            return last
            """
        }
    }

    val bucks = '$'
    templates add f("single()") {
        doc { "Returns single element, or throws exception if there is no or more than one element" }
        returns("T")
        body {
            """
            when (this) {
                is List<*> -> return if (size == 1) this[0] as T else throw IllegalArgumentException("Collection has ${bucks}size elements")
                else -> {
                    val iterator = iterator()
                    if (!iterator.hasNext())
                        throw IllegalArgumentException("Collection is empty")
                    var single = iterator.next()
                    if (iterator.hasNext())
                        throw IllegalArgumentException("Collection has more than one element")
                    return single
                }
            }
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (size != 1)
                throw IllegalArgumentException("Collection has ${bucks}size elements")
            return this[0]
            """
        }
    }

    templates add f("singleOrNull()") {
        doc { "Returns single element, or null if collection is empty, or throws exception if there is more than one element" }
        returns("T?")
        body {
            """
            when (this) {
                is List<*> -> return if (size == 1) this[0] as T else if (size == 0) null else throw IllegalArgumentException("Collection has ${bucks}size elements")
                else -> {
                    val iterator = iterator()
                    if (!iterator.hasNext())
                        return null
                    var single = iterator.next()
                    if (iterator.hasNext())
                        throw IllegalArgumentException("Collection has more than one element")
                    return single
                }
            }
            """
        }
        body(Strings, Lists, ArraysOfObjects, ArraysOfPrimitives) {
            """
            if (size == 0)
                return null
            if (size != 1)
                throw IllegalArgumentException("Collection has ${bucks}size elements")
            return this[0]
            """
        }
    }

    templates add f("single(predicate: (T) -> Boolean)") {
        inline(true)
        doc { "Returns single element matching the given *predicate*, or throws exception if there is no or more than one element" }
        returns("T")
        body {
            """
            var single: T? = null
            var found = false
            for (element in this) {
                if (predicate(element)) {
                    if (found) throw IllegalArgumentException("Collection contains more than one matching element")
                    single = element
                    found = true
                }
            }
            if (!found) throw IllegalArgumentException("Collection doesn't contain any element matching predicate")
            return single as T
            """
        }
    }

    templates add f("singleOrNull(predicate: (T) -> Boolean)") {
        inline(true)
        doc { "Returns single element matching the given *predicate*, or null if element was not found or more than one elements were found" }
        returns("T?")
        body {
            """
            var single: T? = null
            var found = false
            for (element in this) {
                if (predicate(element)) {
                    if (found) throw IllegalArgumentException("Collection contains more than one matching element")
                    single = element
                    found = true
                }
            }
            if (!found) return null
            return single
            """
        }
    }

    return templates
}