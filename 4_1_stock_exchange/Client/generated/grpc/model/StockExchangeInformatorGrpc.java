package grpc.model;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.0)",
    comments = "Source: protos/stock_exchange.proto")
public final class StockExchangeInformatorGrpc {

  private StockExchangeInformatorGrpc() {}

  public static final String SERVICE_NAME = "stockExchange.StockExchangeInformator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.model.ObserveRequest,
      grpc.model.StockInfo> getObserveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "observe",
      requestType = grpc.model.ObserveRequest.class,
      responseType = grpc.model.StockInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.model.ObserveRequest,
      grpc.model.StockInfo> getObserveMethod() {
    io.grpc.MethodDescriptor<grpc.model.ObserveRequest, grpc.model.StockInfo> getObserveMethod;
    if ((getObserveMethod = StockExchangeInformatorGrpc.getObserveMethod) == null) {
      synchronized (StockExchangeInformatorGrpc.class) {
        if ((getObserveMethod = StockExchangeInformatorGrpc.getObserveMethod) == null) {
          StockExchangeInformatorGrpc.getObserveMethod = getObserveMethod =
              io.grpc.MethodDescriptor.<grpc.model.ObserveRequest, grpc.model.StockInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "observe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.ObserveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.StockInfo.getDefaultInstance()))
              .setSchemaDescriptor(new StockExchangeInformatorMethodDescriptorSupplier("observe"))
              .build();
        }
      }
    }
    return getObserveMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.model.Empty,
      grpc.model.Empty> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ping",
      requestType = grpc.model.Empty.class,
      responseType = grpc.model.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.model.Empty,
      grpc.model.Empty> getPingMethod() {
    io.grpc.MethodDescriptor<grpc.model.Empty, grpc.model.Empty> getPingMethod;
    if ((getPingMethod = StockExchangeInformatorGrpc.getPingMethod) == null) {
      synchronized (StockExchangeInformatorGrpc.class) {
        if ((getPingMethod = StockExchangeInformatorGrpc.getPingMethod) == null) {
          StockExchangeInformatorGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<grpc.model.Empty, grpc.model.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new StockExchangeInformatorMethodDescriptorSupplier("ping"))
              .build();
        }
      }
    }
    return getPingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StockExchangeInformatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockExchangeInformatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockExchangeInformatorStub>() {
        @java.lang.Override
        public StockExchangeInformatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockExchangeInformatorStub(channel, callOptions);
        }
      };
    return StockExchangeInformatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StockExchangeInformatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockExchangeInformatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockExchangeInformatorBlockingStub>() {
        @java.lang.Override
        public StockExchangeInformatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockExchangeInformatorBlockingStub(channel, callOptions);
        }
      };
    return StockExchangeInformatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StockExchangeInformatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockExchangeInformatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockExchangeInformatorFutureStub>() {
        @java.lang.Override
        public StockExchangeInformatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockExchangeInformatorFutureStub(channel, callOptions);
        }
      };
    return StockExchangeInformatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StockExchangeInformatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void observe(grpc.model.ObserveRequest request,
        io.grpc.stub.StreamObserver<grpc.model.StockInfo> responseObserver) {
      asyncUnimplementedUnaryCall(getObserveMethod(), responseObserver);
    }

    /**
     */
    public void ping(grpc.model.Empty request,
        io.grpc.stub.StreamObserver<grpc.model.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getObserveMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.model.ObserveRequest,
                grpc.model.StockInfo>(
                  this, METHODID_OBSERVE)))
          .addMethod(
            getPingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.model.Empty,
                grpc.model.Empty>(
                  this, METHODID_PING)))
          .build();
    }
  }

  /**
   */
  public static final class StockExchangeInformatorStub extends io.grpc.stub.AbstractAsyncStub<StockExchangeInformatorStub> {
    private StockExchangeInformatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockExchangeInformatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockExchangeInformatorStub(channel, callOptions);
    }

    /**
     */
    public void observe(grpc.model.ObserveRequest request,
        io.grpc.stub.StreamObserver<grpc.model.StockInfo> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getObserveMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ping(grpc.model.Empty request,
        io.grpc.stub.StreamObserver<grpc.model.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StockExchangeInformatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<StockExchangeInformatorBlockingStub> {
    private StockExchangeInformatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockExchangeInformatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockExchangeInformatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<grpc.model.StockInfo> observe(
        grpc.model.ObserveRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getObserveMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.model.Empty ping(grpc.model.Empty request) {
      return blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StockExchangeInformatorFutureStub extends io.grpc.stub.AbstractFutureStub<StockExchangeInformatorFutureStub> {
    private StockExchangeInformatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockExchangeInformatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockExchangeInformatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.model.Empty> ping(
        grpc.model.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OBSERVE = 0;
  private static final int METHODID_PING = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StockExchangeInformatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StockExchangeInformatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OBSERVE:
          serviceImpl.observe((grpc.model.ObserveRequest) request,
              (io.grpc.stub.StreamObserver<grpc.model.StockInfo>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((grpc.model.Empty) request,
              (io.grpc.stub.StreamObserver<grpc.model.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StockExchangeInformatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StockExchangeInformatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.model.StockExchange.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StockExchangeInformator");
    }
  }

  private static final class StockExchangeInformatorFileDescriptorSupplier
      extends StockExchangeInformatorBaseDescriptorSupplier {
    StockExchangeInformatorFileDescriptorSupplier() {}
  }

  private static final class StockExchangeInformatorMethodDescriptorSupplier
      extends StockExchangeInformatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StockExchangeInformatorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StockExchangeInformatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StockExchangeInformatorFileDescriptorSupplier())
              .addMethod(getObserveMethod())
              .addMethod(getPingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
