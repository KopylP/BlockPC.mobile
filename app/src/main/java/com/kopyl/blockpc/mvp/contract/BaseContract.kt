package com.kopyl.blockpc.mvp.contract

annotation class BaseContract {
    interface View

    abstract class Presenter<V: View> {
        protected lateinit var view: V
        fun attach(view: V){
            this.view = view
        }
        open fun detach() {
        }
    }
}