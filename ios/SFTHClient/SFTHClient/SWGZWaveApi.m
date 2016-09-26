#import "SWGZWaveApi.h"
#import "SWGQueryParamCollection.h"
#import "SWGLightingSummary.h"
#import "SWGDeviceState.h"
#import "SWGApiResponse.h"


@interface SWGZWaveApi ()
    @property (readwrite, nonatomic, strong) NSMutableDictionary *defaultHeaders;
@end

@implementation SWGZWaveApi

static SWGZWaveApi* singletonAPI = nil;

#pragma mark - Initialize methods

- (id) init {
    self = [super init];
    if (self) {
        SWGConfiguration *config = [SWGConfiguration sharedConfig];
        if (config.apiClient == nil) {
            config.apiClient = [[SWGApiClient alloc] init];
        }
        self.apiClient = config.apiClient;
        self.defaultHeaders = [NSMutableDictionary dictionary];
    }
    return self;
}

- (id) initWithApiClient:(SWGApiClient *)apiClient {
    self = [super init];
    if (self) {
        self.apiClient = apiClient;
        self.defaultHeaders = [NSMutableDictionary dictionary];
    }
    return self;
}

#pragma mark -

+(SWGZWaveApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key {

    if (singletonAPI == nil) {
        singletonAPI = [[SWGZWaveApi alloc] init];
        [singletonAPI addHeader:headerValue forKey:key];
    }
    return singletonAPI;
}

+(SWGZWaveApi*) sharedAPI {

    if (singletonAPI == nil) {
        singletonAPI = [[SWGZWaveApi alloc] init];
    }
    return singletonAPI;
}

-(void) addHeader:(NSString*)value forKey:(NSString*)key {
    [self.defaultHeaders setValue:value forKey:key];
}

-(void) setHeaderValue:(NSString*) value
           forKey:(NSString*)key {
    [self.defaultHeaders setValue:value forKey:key];
}

-(unsigned long) requestQueueSize {
    return [SWGApiClient requestQueueSize];
}

#pragma mark - Api Methods

///
/// 
/// 
///  @returns SWGLightingSummary*
///
-(NSNumber*) getLightingSummaryWithCompletionHandler: 
    (void (^)(SWGLightingSummary* output, NSError* error)) handler {

    

    NSMutableString* resourcePath = [NSMutableString stringWithFormat:@"/lightingSummary"];

    // remove format in URL if needed
    if ([resourcePath rangeOfString:@".{format}"].location != NSNotFound) {
        [resourcePath replaceCharactersInRange: [resourcePath rangeOfString:@".{format}"] withString:@".json"];
    }

    NSMutableDictionary *pathParams = [[NSMutableDictionary alloc] init];
    

    NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary* headerParams = [NSMutableDictionary dictionaryWithDictionary:self.defaultHeaders];

    

    // HTTP header `Accept`
    headerParams[@"Accept"] = [SWGApiClient selectHeaderAccept:@[@"application/json"]];
    if ([headerParams[@"Accept"] length] == 0) {
        [headerParams removeObjectForKey:@"Accept"];
    }

    // response content type
    NSString *responseContentType;
    if ([headerParams objectForKey:@"Accept"]) {
        responseContentType = [headerParams[@"Accept"] componentsSeparatedByString:@", "][0];
    }
    else {
        responseContentType = @"";
    }

    // request content type
    NSString *requestContentType = [SWGApiClient selectHeaderContentType:@[]];

    // Authentication setting
    NSArray *authSettings = @[];

    id bodyParam = nil;
    NSMutableDictionary *formParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary *localVarFiles = [[NSMutableDictionary alloc] init];
    
    
    

    
    return [self.apiClient requestWithPath: resourcePath
                                    method: @"GET"
                                pathParams: pathParams
                               queryParams: queryParams
                                formParams: formParams
                                     files: localVarFiles
                                      body: bodyParam
                              headerParams: headerParams
                              authSettings: authSettings
                        requestContentType: requestContentType
                       responseContentType: responseContentType
                              responseType: @"SWGLightingSummary*"
                           completionBlock: ^(id data, NSError *error) {
                               handler((SWGLightingSummary*)data, error);
                           }
          ];
}

///
/// 
/// 
///  @param deviceId  
///
///  @returns SWGDeviceState*
///
-(NSNumber*) getSwitchStateWithDeviceId: (NSString*) deviceId
    completionHandler: (void (^)(SWGDeviceState* output, NSError* error)) handler {

    
    // verify the required parameter 'deviceId' is set
    if (deviceId == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `deviceId` when calling `getSwitchState`"];
    }
    

    NSMutableString* resourcePath = [NSMutableString stringWithFormat:@"/lighting/switches/{deviceId}"];

    // remove format in URL if needed
    if ([resourcePath rangeOfString:@".{format}"].location != NSNotFound) {
        [resourcePath replaceCharactersInRange: [resourcePath rangeOfString:@".{format}"] withString:@".json"];
    }

    NSMutableDictionary *pathParams = [[NSMutableDictionary alloc] init];
    if (deviceId != nil) {
        pathParams[@"deviceId"] = deviceId;
    }
    

    NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary* headerParams = [NSMutableDictionary dictionaryWithDictionary:self.defaultHeaders];

    

    // HTTP header `Accept`
    headerParams[@"Accept"] = [SWGApiClient selectHeaderAccept:@[@"application/json"]];
    if ([headerParams[@"Accept"] length] == 0) {
        [headerParams removeObjectForKey:@"Accept"];
    }

    // response content type
    NSString *responseContentType;
    if ([headerParams objectForKey:@"Accept"]) {
        responseContentType = [headerParams[@"Accept"] componentsSeparatedByString:@", "][0];
    }
    else {
        responseContentType = @"";
    }

    // request content type
    NSString *requestContentType = [SWGApiClient selectHeaderContentType:@[]];

    // Authentication setting
    NSArray *authSettings = @[];

    id bodyParam = nil;
    NSMutableDictionary *formParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary *localVarFiles = [[NSMutableDictionary alloc] init];
    
    
    

    
    return [self.apiClient requestWithPath: resourcePath
                                    method: @"GET"
                                pathParams: pathParams
                               queryParams: queryParams
                                formParams: formParams
                                     files: localVarFiles
                                      body: bodyParam
                              headerParams: headerParams
                              authSettings: authSettings
                        requestContentType: requestContentType
                       responseContentType: responseContentType
                              responseType: @"SWGDeviceState*"
                           completionBlock: ^(id data, NSError *error) {
                               handler((SWGDeviceState*)data, error);
                           }
          ];
}

///
/// 
/// 
///  @param deviceId  
///
///  @param value  
///
///  @returns SWGApiResponse*
///
-(NSNumber*) setDimmerWithDeviceId: (NSString*) deviceId
    value: (NSNumber*) value
    completionHandler: (void (^)(SWGApiResponse* output, NSError* error)) handler {

    
    // verify the required parameter 'deviceId' is set
    if (deviceId == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `deviceId` when calling `setDimmer`"];
    }
    
    // verify the required parameter 'value' is set
    if (value == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `value` when calling `setDimmer`"];
    }
    

    NSMutableString* resourcePath = [NSMutableString stringWithFormat:@"/lighting/dimmers/{deviceId}/{value}"];

    // remove format in URL if needed
    if ([resourcePath rangeOfString:@".{format}"].location != NSNotFound) {
        [resourcePath replaceCharactersInRange: [resourcePath rangeOfString:@".{format}"] withString:@".json"];
    }

    NSMutableDictionary *pathParams = [[NSMutableDictionary alloc] init];
    if (deviceId != nil) {
        pathParams[@"deviceId"] = deviceId;
    }
    if (value != nil) {
        pathParams[@"value"] = value;
    }
    

    NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary* headerParams = [NSMutableDictionary dictionaryWithDictionary:self.defaultHeaders];

    

    // HTTP header `Accept`
    headerParams[@"Accept"] = [SWGApiClient selectHeaderAccept:@[@"application/json"]];
    if ([headerParams[@"Accept"] length] == 0) {
        [headerParams removeObjectForKey:@"Accept"];
    }

    // response content type
    NSString *responseContentType;
    if ([headerParams objectForKey:@"Accept"]) {
        responseContentType = [headerParams[@"Accept"] componentsSeparatedByString:@", "][0];
    }
    else {
        responseContentType = @"";
    }

    // request content type
    NSString *requestContentType = [SWGApiClient selectHeaderContentType:@[]];

    // Authentication setting
    NSArray *authSettings = @[];

    id bodyParam = nil;
    NSMutableDictionary *formParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary *localVarFiles = [[NSMutableDictionary alloc] init];
    
    
    

    
    return [self.apiClient requestWithPath: resourcePath
                                    method: @"POST"
                                pathParams: pathParams
                               queryParams: queryParams
                                formParams: formParams
                                     files: localVarFiles
                                      body: bodyParam
                              headerParams: headerParams
                              authSettings: authSettings
                        requestContentType: requestContentType
                       responseContentType: responseContentType
                              responseType: @"SWGApiResponse*"
                           completionBlock: ^(id data, NSError *error) {
                               handler((SWGApiResponse*)data, error);
                           }
          ];
}

///
/// 
/// sets a dimmer to a specific value on a timer
///  @param deviceId  
///
///  @param value  
///
///  @param minutes  
///
///  @returns SWGApiResponse*
///
-(NSNumber*) setDimmerTimerWithDeviceId: (NSString*) deviceId
    value: (NSNumber*) value
    minutes: (NSNumber*) minutes
    completionHandler: (void (^)(SWGApiResponse* output, NSError* error)) handler {

    
    // verify the required parameter 'deviceId' is set
    if (deviceId == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `deviceId` when calling `setDimmerTimer`"];
    }
    
    // verify the required parameter 'value' is set
    if (value == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `value` when calling `setDimmerTimer`"];
    }
    
    // verify the required parameter 'minutes' is set
    if (minutes == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `minutes` when calling `setDimmerTimer`"];
    }
    

    NSMutableString* resourcePath = [NSMutableString stringWithFormat:@"/lighting/dimmers/{deviceId}/{value}/timer/{minutes}"];

    // remove format in URL if needed
    if ([resourcePath rangeOfString:@".{format}"].location != NSNotFound) {
        [resourcePath replaceCharactersInRange: [resourcePath rangeOfString:@".{format}"] withString:@".json"];
    }

    NSMutableDictionary *pathParams = [[NSMutableDictionary alloc] init];
    if (deviceId != nil) {
        pathParams[@"deviceId"] = deviceId;
    }
    if (value != nil) {
        pathParams[@"value"] = value;
    }
    if (minutes != nil) {
        pathParams[@"minutes"] = minutes;
    }
    

    NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary* headerParams = [NSMutableDictionary dictionaryWithDictionary:self.defaultHeaders];

    

    // HTTP header `Accept`
    headerParams[@"Accept"] = [SWGApiClient selectHeaderAccept:@[@"application/json"]];
    if ([headerParams[@"Accept"] length] == 0) {
        [headerParams removeObjectForKey:@"Accept"];
    }

    // response content type
    NSString *responseContentType;
    if ([headerParams objectForKey:@"Accept"]) {
        responseContentType = [headerParams[@"Accept"] componentsSeparatedByString:@", "][0];
    }
    else {
        responseContentType = @"";
    }

    // request content type
    NSString *requestContentType = [SWGApiClient selectHeaderContentType:@[]];

    // Authentication setting
    NSArray *authSettings = @[];

    id bodyParam = nil;
    NSMutableDictionary *formParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary *localVarFiles = [[NSMutableDictionary alloc] init];
    
    
    

    
    return [self.apiClient requestWithPath: resourcePath
                                    method: @"POST"
                                pathParams: pathParams
                               queryParams: queryParams
                                formParams: formParams
                                     files: localVarFiles
                                      body: bodyParam
                              headerParams: headerParams
                              authSettings: authSettings
                        requestContentType: requestContentType
                       responseContentType: responseContentType
                              responseType: @"SWGApiResponse*"
                           completionBlock: ^(id data, NSError *error) {
                               handler((SWGApiResponse*)data, error);
                           }
          ];
}

///
/// 
/// 
///  @param deviceId  
///
///  @param value  
///
///  @returns SWGApiResponse*
///
-(NSNumber*) setSwitchWithDeviceId: (NSString*) deviceId
    value: (NSString*) value
    completionHandler: (void (^)(SWGApiResponse* output, NSError* error)) handler {

    
    // verify the required parameter 'deviceId' is set
    if (deviceId == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `deviceId` when calling `setSwitch`"];
    }
    
    // verify the required parameter 'value' is set
    if (value == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `value` when calling `setSwitch`"];
    }
    

    NSMutableString* resourcePath = [NSMutableString stringWithFormat:@"/lighting/switches/{deviceId}/{value}"];

    // remove format in URL if needed
    if ([resourcePath rangeOfString:@".{format}"].location != NSNotFound) {
        [resourcePath replaceCharactersInRange: [resourcePath rangeOfString:@".{format}"] withString:@".json"];
    }

    NSMutableDictionary *pathParams = [[NSMutableDictionary alloc] init];
    if (deviceId != nil) {
        pathParams[@"deviceId"] = deviceId;
    }
    if (value != nil) {
        pathParams[@"value"] = value;
    }
    

    NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary* headerParams = [NSMutableDictionary dictionaryWithDictionary:self.defaultHeaders];

    

    // HTTP header `Accept`
    headerParams[@"Accept"] = [SWGApiClient selectHeaderAccept:@[@"application/json"]];
    if ([headerParams[@"Accept"] length] == 0) {
        [headerParams removeObjectForKey:@"Accept"];
    }

    // response content type
    NSString *responseContentType;
    if ([headerParams objectForKey:@"Accept"]) {
        responseContentType = [headerParams[@"Accept"] componentsSeparatedByString:@", "][0];
    }
    else {
        responseContentType = @"";
    }

    // request content type
    NSString *requestContentType = [SWGApiClient selectHeaderContentType:@[]];

    // Authentication setting
    NSArray *authSettings = @[];

    id bodyParam = nil;
    NSMutableDictionary *formParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary *localVarFiles = [[NSMutableDictionary alloc] init];
    
    
    

    
    return [self.apiClient requestWithPath: resourcePath
                                    method: @"POST"
                                pathParams: pathParams
                               queryParams: queryParams
                                formParams: formParams
                                     files: localVarFiles
                                      body: bodyParam
                              headerParams: headerParams
                              authSettings: authSettings
                        requestContentType: requestContentType
                       responseContentType: responseContentType
                              responseType: @"SWGApiResponse*"
                           completionBlock: ^(id data, NSError *error) {
                               handler((SWGApiResponse*)data, error);
                           }
          ];
}

///
/// 
/// sets a switch to a specific value on a timer
///  @param deviceId  
///
///  @param value  
///
///  @param minutes  
///
///  @returns SWGApiResponse*
///
-(NSNumber*) setSwitchTimerWithDeviceId: (NSString*) deviceId
    value: (NSString*) value
    minutes: (NSNumber*) minutes
    completionHandler: (void (^)(SWGApiResponse* output, NSError* error)) handler {

    
    // verify the required parameter 'deviceId' is set
    if (deviceId == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `deviceId` when calling `setSwitchTimer`"];
    }
    
    // verify the required parameter 'value' is set
    if (value == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `value` when calling `setSwitchTimer`"];
    }
    
    // verify the required parameter 'minutes' is set
    if (minutes == nil) {
        [NSException raise:@"Invalid parameter" format:@"Missing the required parameter `minutes` when calling `setSwitchTimer`"];
    }
    

    NSMutableString* resourcePath = [NSMutableString stringWithFormat:@"/lighting/switches/{deviceId}/{value}/timer/{minutes}"];

    // remove format in URL if needed
    if ([resourcePath rangeOfString:@".{format}"].location != NSNotFound) {
        [resourcePath replaceCharactersInRange: [resourcePath rangeOfString:@".{format}"] withString:@".json"];
    }

    NSMutableDictionary *pathParams = [[NSMutableDictionary alloc] init];
    if (deviceId != nil) {
        pathParams[@"deviceId"] = deviceId;
    }
    if (value != nil) {
        pathParams[@"value"] = value;
    }
    if (minutes != nil) {
        pathParams[@"minutes"] = minutes;
    }
    

    NSMutableDictionary* queryParams = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary* headerParams = [NSMutableDictionary dictionaryWithDictionary:self.defaultHeaders];

    

    // HTTP header `Accept`
    headerParams[@"Accept"] = [SWGApiClient selectHeaderAccept:@[@"application/json"]];
    if ([headerParams[@"Accept"] length] == 0) {
        [headerParams removeObjectForKey:@"Accept"];
    }

    // response content type
    NSString *responseContentType;
    if ([headerParams objectForKey:@"Accept"]) {
        responseContentType = [headerParams[@"Accept"] componentsSeparatedByString:@", "][0];
    }
    else {
        responseContentType = @"";
    }

    // request content type
    NSString *requestContentType = [SWGApiClient selectHeaderContentType:@[]];

    // Authentication setting
    NSArray *authSettings = @[];

    id bodyParam = nil;
    NSMutableDictionary *formParams = [[NSMutableDictionary alloc] init];
    NSMutableDictionary *localVarFiles = [[NSMutableDictionary alloc] init];
    
    
    

    
    return [self.apiClient requestWithPath: resourcePath
                                    method: @"POST"
                                pathParams: pathParams
                               queryParams: queryParams
                                formParams: formParams
                                     files: localVarFiles
                                      body: bodyParam
                              headerParams: headerParams
                              authSettings: authSettings
                        requestContentType: requestContentType
                       responseContentType: responseContentType
                              responseType: @"SWGApiResponse*"
                           completionBlock: ^(id data, NSError *error) {
                               handler((SWGApiResponse*)data, error);
                           }
          ];
}



@end
