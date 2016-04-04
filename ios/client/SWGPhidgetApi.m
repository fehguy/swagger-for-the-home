#import "SWGPhidgetApi.h"
#import "SWGFile.h"
#import "SWGApiClient.h"
#import "SWGInputZone.h"
#import "SWGDigitalIO.h"
#import "SWGAnalogIO.h"




@implementation SWGPhidgetApi
static NSString * basePath = @"http://10.0.0.30:8080";

+(SWGPhidgetApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key {
    static SWGPhidgetApi* singletonAPI = nil;

    if (singletonAPI == nil) {
        singletonAPI = [[SWGPhidgetApi alloc] init];
        [singletonAPI addHeader:headerValue forKey:key];
    }
    return singletonAPI;
}

+(void) setBasePath:(NSString*)path {
    basePath = path;
}

+(NSString*) getBasePath {
    return basePath;
}

-(SWGApiClient*) apiClient {
    return [SWGApiClient sharedClientFromPool:basePath];
}

-(void) addHeader:(NSString*)value forKey:(NSString*)key {
    [[self apiClient] setHeaderValue:value forKey:key];
}

-(id) init {
    self = [super init];
    [self apiClient];
    return self;
}

-(void) setHeaderValue:(NSString*) value
           forKey:(NSString*)key {
    [[self apiClient] setHeaderValue:value forKey:key];
}

-(unsigned long) requestQueueSize {
    return [SWGApiClient requestQueueSize];
}


-(NSNumber*) getAnalogInputsWithCompletionBlock: (void (^)(NSArray* output, NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/analog/inputs", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client dictionary: requestUrl 
                               method: @"GET" 
                          queryParams: queryParams 
                                 body: bodyDictionary 
                         headerParams: headerParams
                   requestContentType: requestContentType
                  responseContentType: responseContentType
                      completionBlock: ^(NSDictionary *data, NSError *error) {
                         if (error) {
                             completionBlock(nil, error);return;
                         }
                         
                         if([data isKindOfClass:[NSArray class]]){
                             NSMutableArray * objs = [[NSMutableArray alloc] initWithCapacity:[data count]];
                             for (NSDictionary* dict in (NSArray*)data) {
                                SWGAnalogIO* d = [[SWGAnalogIO alloc]initWithValues: dict];
                                [objs addObject:d];
                             }
                             completionBlock(objs, nil);
                         }
                        }];
    

}

-(NSNumber*) getInputZonesWithCompletionBlock: (void (^)(NSArray* output, NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/inputDefinition", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client dictionary: requestUrl 
                               method: @"GET" 
                          queryParams: queryParams 
                                 body: bodyDictionary 
                         headerParams: headerParams
                   requestContentType: requestContentType
                  responseContentType: responseContentType
                      completionBlock: ^(NSDictionary *data, NSError *error) {
                         if (error) {
                             completionBlock(nil, error);return;
                         }
                         
                         if([data isKindOfClass:[NSArray class]]){
                             NSMutableArray * objs = [[NSMutableArray alloc] initWithCapacity:[data count]];
                             for (NSDictionary* dict in (NSArray*)data) {
                                SWGInputZone* d = [[SWGInputZone alloc]initWithValues: dict];
                                [objs addObject:d];
                             }
                             completionBlock(objs, nil);
                         }
                        }];
    

}

-(NSNumber*) setLcdWithCompletionBlock:(NSString*) msg
        lineNumber:(NSNumber*) lineNumber
        completionHandler: (void (^)(NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/lcd", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    if(msg != nil)
        queryParams[@"msg"] = msg;
    if(lineNumber != nil)
        queryParams[@"lineNumber"] = lineNumber;
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        if(msg == nil) {
        // error
    }
    if(lineNumber == nil) {
        // error
    }
    SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client stringWithCompletionBlock:requestUrl 
                                             method:@"POST" 
                                        queryParams:queryParams 
                                               body:bodyDictionary 
                                       headerParams:headerParams
                                 requestContentType: requestContentType
                                responseContentType: responseContentType
                                    completionBlock:^(NSString *data, NSError *error) {
                        if (error) {
                            completionBlock(error);
                            return;
                        }
                        completionBlock(nil);
                    }];
    

}

-(NSNumber*) setAllRelayOutputWithCompletionBlock:(NSNumber*) state
        completionHandler: (void (^)(NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/relay/output", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    if(state != nil)
        queryParams[@"state"] = state;
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        if(state == nil) {
        // error
    }
    SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client stringWithCompletionBlock:requestUrl 
                                             method:@"POST" 
                                        queryParams:queryParams 
                                               body:bodyDictionary 
                                       headerParams:headerParams
                                 requestContentType: requestContentType
                                responseContentType: responseContentType
                                    completionBlock:^(NSString *data, NSError *error) {
                        if (error) {
                            completionBlock(error);
                            return;
                        }
                        completionBlock(nil);
                    }];
    

}

-(NSNumber*) getRelayOutputWithCompletionBlock:(NSNumber*) position
        completionHandler: (void (^)(SWGDigitalIO* output, NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/relay/output/{position}", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:[NSString stringWithFormat:@"%@%@%@", @"{", @"position", @"}"]] withString: [SWGApiClient escape:position]];
    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        if(position == nil) {
        // error
    }
    SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client dictionary:requestUrl 
                              method:@"GET" 
                         queryParams:queryParams 
                                body:bodyDictionary 
                        headerParams:headerParams
                  requestContentType:requestContentType
                 responseContentType:responseContentType
                     completionBlock:^(NSDictionary *data, NSError *error) {
                        if (error) {
                            completionBlock(nil, error);return;
                        }
                        SWGDigitalIO *result = nil;
                        if (data) {
                            result = [[SWGDigitalIO alloc]initWithValues: data];
                        }
                        completionBlock(result , nil);}];
    

}

-(NSNumber*) setOutputRelayWithCompletionBlock:(NSNumber*) state
        position:(NSNumber*) position
        completionHandler: (void (^)(SWGDigitalIO* output, NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/relay/output/{position}", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:[NSString stringWithFormat:@"%@%@%@", @"{", @"position", @"}"]] withString: [SWGApiClient escape:position]];
    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    if(state != nil)
        queryParams[@"state"] = state;
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        if(state == nil) {
        // error
    }
    if(position == nil) {
        // error
    }
    SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client dictionary:requestUrl 
                              method:@"POST" 
                         queryParams:queryParams 
                                body:bodyDictionary 
                        headerParams:headerParams
                  requestContentType:requestContentType
                 responseContentType:responseContentType
                     completionBlock:^(NSDictionary *data, NSError *error) {
                        if (error) {
                            completionBlock(nil, error);return;
                        }
                        SWGDigitalIO *result = nil;
                        if (data) {
                            result = [[SWGDigitalIO alloc]initWithValues: data];
                        }
                        completionBlock(result , nil);}];
    

}

-(NSNumber*) updateRelayZoneWithCompletionBlock:(NSNumber*) state
        name:(NSString*) name
        completionHandler: (void (^)(SWGDigitalIO* output, NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/relay/zone/{name}", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:[NSString stringWithFormat:@"%@%@%@", @"{", @"name", @"}"]] withString: [SWGApiClient escape:name]];
    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    if(state != nil)
        queryParams[@"state"] = state;
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        if(state == nil) {
        // error
    }
    if(name == nil) {
        // error
    }
    SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client dictionary:requestUrl 
                              method:@"POST" 
                         queryParams:queryParams 
                                body:bodyDictionary 
                        headerParams:headerParams
                  requestContentType:requestContentType
                 responseContentType:responseContentType
                     completionBlock:^(NSDictionary *data, NSError *error) {
                        if (error) {
                            completionBlock(nil, error);return;
                        }
                        SWGDigitalIO *result = nil;
                        if (data) {
                            result = [[SWGDigitalIO alloc]initWithValues: data];
                        }
                        completionBlock(result , nil);}];
    

}

-(NSNumber*) relayOnWithTimerWithCompletionBlock:(NSString*) name
        timer:(NSNumber*) timer
        completionHandler: (void (^)(NSError* error))completionBlock{

    NSMutableString* requestUrl = [NSMutableString stringWithFormat:@"%@/phidget/relay/zone/{name}/timer", basePath];

    // remove format in URL if needed
    if ([requestUrl rangeOfString:@".{format}"].location != NSNotFound)
        [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:@".{format}"] withString:@".json"];

    [requestUrl replaceCharactersInRange: [requestUrl rangeOfString:[NSString stringWithFormat:@"%@%@%@", @"{", @"name", @"}"]] withString: [SWGApiClient escape:name]];
    NSString* requestContentType = @"application/json";
    NSString* responseContentType = @"application/json";

        NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    if(timer != nil)
        queryParams[@"timer"] = timer;
    NSMutableDictionary* headerParams = [[NSMutableDictionary alloc] init];
    id bodyDictionary = nil;
        if(name == nil) {
        // error
    }
    if(timer == nil) {
        // error
    }
    SWGApiClient* client = [SWGApiClient sharedClientFromPool:basePath];

    return [client stringWithCompletionBlock:requestUrl 
                                             method:@"POST" 
                                        queryParams:queryParams 
                                               body:bodyDictionary 
                                       headerParams:headerParams
                                 requestContentType: requestContentType
                                responseContentType: responseContentType
                                    completionBlock:^(NSString *data, NSError *error) {
                        if (error) {
                            completionBlock(error);
                            return;
                        }
                        completionBlock(nil);
                    }];
    

}



@end
