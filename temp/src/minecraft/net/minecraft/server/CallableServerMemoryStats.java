package net.minecraft.server;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableServerMemoryStats implements Callable {

   // $FF: synthetic field
   final MinecraftServer field_92075_a;


   public CallableServerMemoryStats(MinecraftServer p_i5042_1_) {
      this.field_92075_a = p_i5042_1_;
   }

   public String func_92074_a() {
      return MinecraftServer.func_71196_a(this.field_92075_a).func_72394_k() + " / " + MinecraftServer.func_71196_a(this.field_92075_a).func_72352_l() + "; " + MinecraftServer.func_71196_a(this.field_92075_a).field_72404_b;
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_92074_a();
   }
}
